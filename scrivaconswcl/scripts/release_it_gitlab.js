// Copyright Regione Piemonte - 2025
// SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
// SPDX-License-Identifier: EUPL-1.2
// ============LICENSE_END===================
const fs = require("fs");
const _ = require("lodash");
const path = require("path");
const globby = require("globby");
const FormData = require("form-data");
const { format } = require("release-it/lib/util");
const prompts = require("release-it/lib/plugin/gitlab/prompts");

const { Plugin } = require("release-it");
const { request } = require("https");

const noop = Promise.resolve();
const options = { write: false };
const changelogFallback = 'git log --pretty=format:"* %s (%h)"';

const docs = "https://git.io/release-it-gitlab";

class CustomGitLab extends Plugin {
  constructor(...args) {
    super(...args);
    this.registerPrompts(prompts);
    this.type = "GitLab";
    this.assets = [];
  }

  getInitialOptions(options, namespace) {
    return Object.assign({}, options[namespace], {
      isUpdate: options.isUpdate,
    });
  }

  get hostname() {
    return this.options.hostname;
  }

  get path() {
    return this.options.path;
  }

  get releaseUrl() {
    return this.options.releaseUrl;
  }

  get token() {
    const { tokenRef } = this.options;
    return _.get(process.env, tokenRef, null);
  }

  async base_init() {
    await this.fetch();
    const latestTagName = await this.getLatestTagName();
    const secondLatestTagName = this.options.isUpdate
      ? await this.getSecondLatestTagName()
      : null;
    const tagTemplate =
      this.options.tagName ||
      ((latestTagName || "").match(/^v/) ? "v${version}" : "${version}");
    this.setContext({ tagTemplate, latestTagName, secondLatestTagName });
    this.config.setContext({ latestTag: latestTagName });
  }

  async init() {
    if (!this.token) {
      throw e(
        `Environment variable "${tokenRef}" is required for GitLab releases.`,
        docs
      );
    }
  }

  async getChangelog() {
    const { isUpdate, latestTagName, secondLatestTagName } = this.getContext();
    const context = {
      latestTag: latestTagName,
      from: latestTagName,
      to: "HEAD",
    };
    const { changelog } = this.options;
    if (!changelog) return null;

    if (latestTagName && isUpdate) {
      context.from = secondLatestTagName;
      context.to = `${latestTagName}^1`;
    }

    if (!context.from && changelog.includes("${from}")) {
      return this.exec(changelogFallback);
    }

    return this.exec(changelog, { context, options });
  }

  async beforeRelease() {
    const { releaseNotes: script } = this.options;
    const { changelog } = this.config.getContext();
    const releaseNotes = script ? await this.exec(script) : changelog;
    this.setContext({ releaseNotes });
    if (releaseNotes !== changelog) {
      this.log.preview({ title: "release notes", text: releaseNotes });
    }
  }

  bump(version) {
    const { tagTemplate } = this.getContext();
    const tagName = format(tagTemplate, { version }) || version;
    this.setContext({ version, tagName });
    this.config.setContext({ tagName });
  }

  afterRelease() {
    const { isReleased } = this.getContext();
    if (isReleased) {
      this.log.log(`🔗 ${this.releaseUrl}`);
    }
  }

  async release() {
    const glRelease = () => this.createRelease();
    const glUploadAssets = () => this.uploadAssets();

    if (this.config.isCI) {
      await this.step({
        enabled: this.options.assets,
        task: glUploadAssets,
        label: "GitLab upload assets",
      });
      return await this.step({ task: glRelease, label: "GitLab release" });
    } else {
      // const release = () => glUploadAssets().then(() => glRelease());
      return await this.step({
        task: glRelease,
        label: "GitLab release",
        prompt: "release",
      });
    }
  }

  async httpRequest(releaseOptions) {
    return new Promise((resolve, reject) => {
      const options = {
        method: "POST",
        hostname: this.hostname,
        path: this.path,
        headers: {
          "PRIVATE-TOKEN": this.token,
          "Content-Type": "application/json",
        },
      };
      this.debug(options);
      const req = request(options, (res) => {
        const chunks = [];

        res.on("error", (error) => {
          this.debug(error);
          reject(error);
        });

        res.on("data", (chunk) => {
          chunks.push(chunk);
        });

        res.on("end", () => {
          try {
            const body = Buffer.concat(chunks);
            const jsonResponse = JSON.parse(body.toString());
            if (
              jsonResponse.tag_name &&
              jsonResponse.name &&
              jsonResponse.created_at
            ) {
              resolve({ body: jsonResponse });
            } else {
              this.debug(jsonResponse.error);
              reject(jsonResponse.error);
            }
          } catch (e) {
            reject(e);
            this.debug(e);
          }
        });
      });
      req.write(JSON.stringify(releaseOptions));
      req.end();
    });
  }

  async request(options) {
    this.debug(
      Object.assign({ url: `${this.hostname}/${this.path}` }, options)
    );

    const response = await this.httpRequest(options);

    const body =
      typeof response.body === "string"
        ? JSON.parse(response.body)
        : response.body || {};
    this.debug(body);
    return body;
  }

  async createRelease() {
    const { releaseName } = this.options;
    const { tagName, releaseNotes } = this.getContext();
    const { isDryRun } = this.config;
    const name = format(releaseName, this.config.getContext());
    const description = releaseNotes || "-";

    this.log.exec(`gitlab releases#createRelease "${name}" (${tagName})`, {
      isDryRun,
    });

    if (isDryRun) {
      this.setContext({ isReleased: true });
      return true;
    }

    const options = {
      name,
      tag_name: tagName,
      description,
    };

    if (this.assets.length) {
      options.assets = {
        links: this.assets,
      };
    }

    try {
      await this.request(options);
      this.log.verbose("gitlab releases#createRelease: done");
      this.setContext({ isReleased: true });
      return true;
    } catch (err) {
      this.debug(err);
      throw err;
    }
  }

  async uploadAsset(filePath) {
    const name = path.basename(filePath);
    const { repository } = this.getContext("repo");
    const endpoint = `projects/${this.id}/uploads`;

    const body = new FormData();
    body.append("file", fs.createReadStream(filePath));
    const options = { body };

    try {
      const body = await this.request(endpoint, options);
      this.log.verbose(`gitlab releases#uploadAsset: done (${body.url})`);
      this.assets.push({
        name,
        url: `${this.origin}/${repository}${body.url}`,
      });
    } catch (err) {
      this.debug(err);
      throw err;
    }
  }

  uploadAssets() {
    const { assets } = this.options;
    const { isDryRun } = this.config;

    this.log.exec("gitlab releases#uploadAssets", assets, { isDryRun });

    if (!assets || isDryRun) {
      return noop;
    }

    return globby(assets).then((files) => {
      if (!files.length) {
        this.log.warn(
          `gitlab releases#uploadAssets: could not find "${assets}" relative to ${process.cwd()}`
        );
      }
      return Promise.all(files.map((filePath) => this.uploadAsset(filePath)));
    });
  }

  fetch() {
    return this.exec("git fetch").catch((err) => {
      this.debug(err);
      new Error(`Unable to fetch from ${this.remoteUrl}${EOL}${err.message}`);
    });
  }

  getLatestTagName() {
    return this.exec("git describe --tags --abbrev=0", { options }).then(
      (stdout) => stdout || null,
      () => null
    );
  }

  async getSecondLatestTagName() {
    const sha = await this.exec("git rev-list --tags --skip=1 --max-count=1", {
      options,
    });
    return this.exec(`git describe --tags --abbrev=0 ${sha}`, {
      options,
    }).catch(() => null);
  }
}

module.exports = CustomGitLab;
