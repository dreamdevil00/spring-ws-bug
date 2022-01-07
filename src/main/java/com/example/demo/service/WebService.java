package com.example.demo.service;

import org.springframework.boot.webservices.client.WebServiceTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.client.core.WebServiceMessageExtractor;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.saaj.SaajSoapMessage;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.w3c.dom.CDATASection;

import javax.xml.soap.*;
import javax.xml.transform.TransformerException;
import java.io.IOException;

/**
 * @author zero
 */
@Service
public class WebService {
    private final WebServiceTemplate webServiceTemplate;

    public WebService(WebServiceTemplateBuilder webServiceTemplateBuilder) {
        Wss4jSecurityInterceptor wss4jSecurityInterceptor = new Wss4jSecurityInterceptor();
        wss4jSecurityInterceptor.setSecurementActions("UsernameToken");
        wss4jSecurityInterceptor.setSecurementUsername("Bob");
        wss4jSecurityInterceptor.setSecurementPasswordType("PasswordText");
        wss4jSecurityInterceptor.setSecurementPassword("password");
        this.webServiceTemplate = webServiceTemplateBuilder
                .setDefaultUri("http://www.example.com/")
                .interceptors(wss4jSecurityInterceptor)
                .build();
    }

    public void bar() {
        this.webServiceTemplate.sendAndReceive(message -> {
            SaajSoapMessage saajSoapMessage = ((SaajSoapMessage) message);
            SOAPMessage soapMessage = saajSoapMessage.getSaajMessage();
            try {
                SOAPPart soapPart = soapMessage.getSOAPPart();
                CDATASection cdataSection = soapPart.createCDATASection("<req>Hello World!</req>");
                SOAPBody body = soapMessage.getSOAPBody();
                SOAPElement echo = body.addChildElement("echo");
                echo.appendChild(cdataSection);
                soapMessage.saveChanges();

            } catch (SOAPException e) {
                e.printStackTrace();
            }
        }, new WebServiceMessageExtractor<Object>() {
            @Override
            public Object extractData(WebServiceMessage webServiceMessage) throws IOException, TransformerException {
                return null;
            }
        });
    }
}
