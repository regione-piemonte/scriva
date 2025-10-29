/*-
 * ========================LICENSE_START=================================
 * 
 * Copyright (C) 2025 Regione Piemonte
 * 
 * SPDX-FileCopyrightText: (C) Copyright 2025 Regione Piemonte
 * SPDX-License-Identifier: EUPL-1.2
 * =========================LICENSE_END==================================
 */
package it.csi.scriva.scrivabesrv.business.be.helper.piemontepay;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import it.csi.scriva.scrivabesrv.business.be.exception.GenericException;
import it.csi.scriva.scrivabesrv.business.be.helper.AbstractServiceHelper;
import it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.dto.PPayPagamentoMarcaBolloInDTO;
import it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.dto.PPayPagamentoMarcaBolloOutDTO;
import it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.dto.PPayRtInDTO;
import it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.dto.PPayRtOutDTO;
import it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.dto.PaymentDataResult;
import it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.dto.PaymentDataStampTax;
import it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.dto.PaymentStampTaxReference;
import it.csi.scriva.scrivabesrv.business.be.helper.piemontepay.dto.Status;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import javax.validation.constraints.NotNull;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Base64;

/**
 * The type Piemonte pay service helper.
 *
 * @author CSI PIEMONTE
 */
public class PiemontePayServiceHelper extends AbstractServiceHelper {

    private final String className = this.getClass().getSimpleName();
    /**
     * The constant ORGANIZATIONS.
     */
    public static final String ORGANIZATIONS = "/organizations/";
    /**
     * The constant PAYMENTTYPES.
     */
    public static final String PAYMENTTYPES = "/paymenttypes/";
    /**
     * The constant ORGANIZATION.
     */
    public static final String ORGANIZATION = "organization : [";
    /**
     * The constant PAYMENT_TYPE.
     */
    public static final String PAYMENT_TYPE = "] - paymentType : [";
    /**
     * The constant OUTPUT.
     */
    public static final String OUTPUT = "\n\nOUTPUT:\n";
    /**
     * The constant INPUT.
     */
    public static final String INPUT = "\n\nINPUT:\n";
    /**
     * The constant URL.
     */
    public static final String URL = "url : [";
    /**
     * The constant POST_INPUT.
     */
    public static final String POST_INPUT = "POST input :\n ";

    private String user;
    private String password;


    private final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).findAndRegisterModules();

    /**
     * Instantiates a new Piemonte pay service helper.
     *
     * @param endPoint   the end point
     * @param serviceUrl the service url
     */
    public PiemontePayServiceHelper(String endPoint, String serviceUrl) {
        this.apiEndpoint = endPoint + serviceUrl;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Pagamento marca da bollo p pay pagamento marca bollo out dto.
     *
     * @param pPayPagamentoMarcaBolloInput the p pay pagamento marca bollo input
     * @return the p pay pagamento marca bollo out dto
     * @throws GenericException the exception
     */
    public PPayPagamentoMarcaBolloOutDTO pagamentoMarcaDaBollo(PPayPagamentoMarcaBolloInDTO pPayPagamentoMarcaBolloInput) throws GenericException {
        logBeginInfo(className, pPayPagamentoMarcaBolloInput);
        PPayPagamentoMarcaBolloOutDTO result = new PPayPagamentoMarcaBolloOutDTO();
        String url = apiEndpoint + "/pagamentoMarcaBollo";
        logDebug(className, URL + url + "]");
        String jsonInput;
        try {
            jsonInput = objectMapper.writeValueAsString(pPayPagamentoMarcaBolloInput);
            logDebug(className, POST_INPUT + jsonInput + "\n");

            Entity<PPayPagamentoMarcaBolloInDTO> entity = Entity.json(pPayPagamentoMarcaBolloInput);
            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);
            if (resp.getStatus() >= 400) {
                String error = INPUT + jsonInput + "\n";
                error += OUTPUT + resp.readEntity(String.class) + "\n";
                logError(className, (StringUtils.isNotBlank(error) ? error : String.valueOf(resp.getStatus())));
                //inviare mail
                throw new GenericException(error);
            } else {
                result = resp.readEntity(new GenericType<PPayPagamentoMarcaBolloOutDTO>() {
                });
                logInfo(className, "POST output :\n " + result + "\n");
            }
        } catch (Exception e) {
            logError(className, e);
            throw new GenericException(e);
        }
        return result;

    }

    /**
     * Ger rt p pay rt out dto.
     *
     * @param pPayRtInput the p pay rt input
     * @return the p pay rt out dto
     */
    public PPayRtOutDTO gerRT(PPayRtInDTO pPayRtInput) {
        logBeginInfo(className, pPayRtInput);
        PPayRtOutDTO result = null;
        String url = apiEndpoint + "/getRT";
        logDebug(className, URL + url + "]");
        try {
            String jsonInput = objectMapper.writeValueAsString(pPayRtInput);
            logDebug(className, POST_INPUT + jsonInput + "\n");

            Entity<PPayRtInDTO> entity = Entity.json(pPayRtInput);
            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(entity);
            if (resp.getStatus() > 400) {
                String error = resp.readEntity(String.class);
                logError(className, (StringUtils.isNotBlank(error) ? error : String.valueOf(resp.getStatus())));
            } else {
                result = resp.readEntity(new GenericType<PPayRtOutDTO>() {
                });
                logInfo(className, "POST output :\n " + result + "\n");
            }
        } catch (Exception e) {
            logError(className, e);
        }
        return result;
    }

    /*************************
     *      NEW
     *************************/


    /**
     * Stamp tax payment payment stamp tax reference.
     *
     * @param organization        the organization
     * @param paymentType         the payment type
     * @param paymentDataStampTax the payment data stamp tax
     * @return the payment stamp tax reference
     * @throws GenericException the exception
     */
    public PaymentStampTaxReference stampTaxPayment(@NotNull String organization, @NotNull String paymentType, @NotNull PaymentDataStampTax paymentDataStampTax) throws GenericException {
        logBeginInfo(className, ORGANIZATION + organization + PAYMENT_TYPE + paymentType + "]\nPaymentDataStampTax:\n" + paymentDataStampTax);
        PaymentStampTaxReference result = new PaymentStampTaxReference();
        String url = apiEndpoint + ORGANIZATIONS + organization + PAYMENTTYPES + paymentType + "/stamptaxpayment";
        logDebug(className, URL + url + "]");
        String jsonInput;
        try {
            jsonInput = objectMapper.writeValueAsString(paymentDataStampTax);
            logDebug(className, POST_INPUT + jsonInput + "\n");
            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .post(Entity.json(paymentDataStampTax));
            resp.bufferEntity();
            String logResponse = INPUT + jsonInput + OUTPUT + resp.readEntity(String.class) + "\n";
            if (resp.getStatus() >= 400) {
                logError(className, (StringUtils.isNotBlank(logResponse) ? logResponse : String.valueOf(resp.getStatus())));
                throw new GenericException(logResponse);
            } else {
                result = resp.readEntity(new GenericType<PaymentStampTaxReference>() {
                });
                logInfo(className, logResponse);
            }
        } catch (Exception e) {
            logError(className, e);
            throw new GenericException(e);
        }
        return result;
    }

    /**
     * Debt positions status status.
     *
     * @param organization the organization
     * @param paymentType  the payment type
     * @param iuv          the iuv
     * @return the status
     * @throws GenericException the generic exception
     */
    public Status debtPositionsStatus(@NotNull String organization, @NotNull String paymentType, @NotNull String iuv) throws GenericException {
        logBeginInfo(className, ORGANIZATION + organization + PAYMENT_TYPE + paymentType + "] - iuv : [" + iuv + "]");
        Status result = new Status();
        String url = apiEndpoint + ORGANIZATIONS + organization + PAYMENTTYPES + paymentType + "/debtpositions/" + iuv + "/status";
        logDebug(className, URL + url + "]");
        try {
            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .get();
            resp.bufferEntity();
            String logResponse = INPUT + url + OUTPUT + resp.readEntity(String.class) + "\n";
            if (resp.getStatus() >= 400) {
                logError(className, (StringUtils.isNotBlank(logResponse) ? logResponse : String.valueOf(resp.getStatus())));
                throw new GenericException(logResponse);
            } else {
                result = resp.readEntity(new GenericType<Status>() {
                });
                logInfo(className, logResponse);
            }
        } catch (Exception e) {
            logError(className, e);
            throw new GenericException(e);
        }
        return result;
    }

    /**
     * Debt positions payment notice.
     *
     * @param organization the organization
     * @param paymentType  the payment type
     * @param iuv          the iuv
     * @return the payment data result
     * @throws GenericException the generic exception
     */
    public PaymentDataResult debtPositionsPaymentNotice(@NotNull String organization, @NotNull String paymentType, @NotNull String iuv) throws GenericException {
        logBeginInfo(className, ORGANIZATION + organization + PAYMENT_TYPE + paymentType + "] - iuv : [" + iuv + "]");
        PaymentDataResult result = new PaymentDataResult();
        String url = apiEndpoint + ORGANIZATIONS + organization + PAYMENTTYPES + paymentType + "/debtpositions/" + iuv + "/paymentnotice";
        logDebug(className, URL + url + "]");
        try {
            Response resp = getBuilder(url)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                    .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON)
                    .get();
            resp.bufferEntity();
            String logResponse = INPUT + url + OUTPUT + resp.readEntity(String.class) + "\n";
            if (resp.getStatus() >= 400) {
                logError(className, (StringUtils.isNotBlank(logResponse) ? logResponse : String.valueOf(resp.getStatus())));
                throw new GenericException(logResponse);
            } else {
                result = resp.readEntity(new GenericType<PaymentDataResult>() {
                });
                logInfo(className, logResponse);
            }
        } catch (Exception e) {
            logError(className, e);
            throw new GenericException(e);
        }
        return result;
    }


    @Override
    public Invocation.Builder getBuilder(String url) {
        final X509TrustManager x509TrustManager = getX509TrustManager();
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        //return new X509Certificate[0];
                        return x509TrustManager.getAcceptedIssuers();
                    }

                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) throws CertificateException {
                        x509TrustManager.checkServerTrusted(certs, authType);
                    }

                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) throws CertificateException {
                        // All server  document why this method is empty
                        x509TrustManager.checkClientTrusted(certs, authType);
                    }
                }
        };

        SSLContext sslContext = null;
        try {
            //sslContext = SSLContext.getInstance("SSL");
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            logError(className, e);
        }


        Client client = ClientBuilder.newBuilder().sslContext(sslContext).hostnameVerifier(NoopHostnameVerifier.INSTANCE).build();

        //Client client = ClientBuilder.newBuilder().build();
        String encoding = Base64.getEncoder().encodeToString((user + ":" + password).getBytes(StandardCharsets.UTF_8));
        return client.target(url).request()
                .header(HttpHeaders.AUTHORIZATION, "Basic " + encoding);
    }

    /**
     * Gets trust manager factory.
     *
     * @return the trust manager factory
     */
    private TrustManagerFactory getTrustManagerFactory() {
        TrustManagerFactory trustManagerFactory = null;
        try {
            trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
        } catch (Exception e) {
            logError(className, e);
        }
        return trustManagerFactory;
    }

    /**
     * Gets x 509 trust manager.
     *
     * @return the x 509 trust manager
     */
    private X509TrustManager getX509TrustManager() {
        TrustManagerFactory trustManagerFactory = getTrustManagerFactory();
        X509TrustManager x509TrustManager = null;
        if (trustManagerFactory != null) {
            for (TrustManager tm : trustManagerFactory.getTrustManagers()) {
                if (tm instanceof X509TrustManager) {
                    x509TrustManager = (X509TrustManager) tm;
                    break;
                }
            }
        }
        return x509TrustManager;
    }


}