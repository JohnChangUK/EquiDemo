package lambda.handler;

import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import lambda.request.PersonRequest;
import lambda.response.PersonResponse;

public class SavePersonHandler implements RequestHandler<PersonRequest, PersonResponse> {

    private DynamoDB dynamoDB;
    private String DYNAMODB_TABLE_NAME = "Person";
    private String EU_WEST_2 = "eu-west-2";

    @Override
    public PersonResponse handleRequest(PersonRequest personRequest, Context context) {
        initDynamoDbClient();
        persistData(personRequest);

        PersonResponse personResponse = new PersonResponse();
        personResponse.setMessage("Context function invoked: " + context.getInvokedFunctionArn());

        return personResponse;
    }

    private void persistData(PersonRequest personRequest) {
        dynamoDB.getTable(DYNAMODB_TABLE_NAME)
                .putItem(new PutItemSpec().withItem(new Item()
                        .withInt("id", Integer.valueOf(personRequest.getId()))
                        .withString("firstName", personRequest.getFirstName())
                        .withString("lastName", personRequest.getLastName())));
    }

    private void initDynamoDbClient() {
        AmazonDynamoDBClient client = new AmazonDynamoDBClient();
        client.setRegion(RegionUtils.getRegion(EU_WEST_2));
        dynamoDB = new DynamoDB(client);
    }
}
