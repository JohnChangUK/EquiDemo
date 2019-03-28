package lambda.handler;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import model.Person;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class APIHandler implements RequestStreamHandler {

    private static final String DYNAMODB_TABLE_NAME = System.getenv("TABLE_NAME");

    @Override
    public void handleRequest(
            InputStream inputStream, OutputStream outputStream, Context context) throws IOException {

        JSONParser jsonParser = new JSONParser();
        JSONObject responseJson = new JSONObject();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            AmazonDynamoDB client = AmazonDynamoDBClientBuilder.defaultClient();
            DynamoDB dynamoDB = new DynamoDB(client);
            JSONObject event = (JSONObject) jsonParser.parse(reader);

            if (event.get("body") != null) {
                Person person = new Person((String) event.get("body"));

                dynamoDB.getTable("Person")
                        .putItem(new PutItemSpec()
                                .withItem(new Item()
                                        .withNumber("id", person.getId())
                                        .withString("name", person.getName())));
            }

            JSONObject responseBody = new JSONObject();
            responseBody.put("message", "New person created");

            JSONObject headerJson = new JSONObject();
            headerJson.put("x-custom-header", "Custom header value");

            responseJson.put("statusCode", 200);
            responseJson.put("headers", headerJson);
            responseJson.put("body", responseBody.toString());
        } catch (ParseException e) {
            responseJson.put("statusCode", 400);
            responseJson.put("exception", e);
        }

        OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        writer.write(responseJson.toString());
        writer.close();
    }

    public void handleGetByParam(
            InputStream inputStream, OutputStream outputStream, Context context) {

    }
}
