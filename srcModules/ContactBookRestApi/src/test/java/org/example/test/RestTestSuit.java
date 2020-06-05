package org.example.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.example.dto.ContactDto;
import org.example.dto.PhoneNumberDto;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

@Test
public class RestTestSuit {

    private String baseUrl;
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @BeforeClass
    @Parameters({ "base-url" })
    public void init(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Test
    public void getAllContacts() throws IOException {
        HttpGet httpGet = new HttpGet(baseUrl);
        try (CloseableHttpClient httpclient = HttpClients.createDefault();
                CloseableHttpResponse httpResponse = httpclient.execute(httpGet)) {
            Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_OK);
        }
    }

    @Test
    public void getMissingContacts() throws IOException {
        HttpGet httpGet = new HttpGet(String.format("%s/-1", baseUrl));
        try (CloseableHttpClient httpclient = HttpClients.createDefault();
                CloseableHttpResponse httpResponse = httpclient.execute(httpGet)) {
            Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_NOT_FOUND);
        }
    }


    @Test
    public void createContact() throws IOException {
        HttpPost httppost = new HttpPost(baseUrl);
        httppost.setEntity(new StringEntity(OBJECT_MAPPER.writeValueAsString(getContact()), ContentType.APPLICATION_JSON));

        try (CloseableHttpClient httpclient = HttpClients.createDefault();
                CloseableHttpResponse httpResponse = httpclient.execute(httppost)) {
            Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_CREATED);
        }
    }

    @Test
    public void createInvalidContact() throws IOException {
        HttpPost httppost = new HttpPost(baseUrl);
        ContactDto contact = getContact();
        contact.setName(null);
        httppost.setEntity(new StringEntity(OBJECT_MAPPER.writeValueAsString(contact), ContentType.APPLICATION_JSON));

        try (CloseableHttpClient httpclient = HttpClients.createDefault();
                CloseableHttpResponse httpResponse = httpclient.execute(httppost)) {
            Assert.assertEquals(httpResponse.getStatusLine().getStatusCode(), HttpStatus.SC_BAD_REQUEST);
        }
    }

    private ContactDto getContact() {
        ContactDto contact = new ContactDto();
        contact.setName("Test contact");
        contact.setAddress("Test contact address");
        PhoneNumberDto phoneNumber = new PhoneNumberDto();
        phoneNumber.setDescription("Personal number");
        phoneNumber.setNumber("+351465415465");
        PhoneNumberDto workNumber = new PhoneNumberDto();
        workNumber.setDescription("Work number");
        workNumber.setNumber("+351987654231");
        contact.setPhoneNumbers(Stream.of(phoneNumber, workNumber).collect(Collectors.toList()));
        return contact;

    }

}
