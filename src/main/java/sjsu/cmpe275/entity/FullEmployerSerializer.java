package sjsu.cmpe275.entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import sjsu.cmpe275.entity.Address;
import sjsu.cmpe275.entity.Employee;
import sjsu.cmpe275.entity.Employer;

import java.io.IOException;
import java.util.List;

public class FullEmployerSerializer extends JsonSerializer<Employer> {

    @Override
    public void serialize(Employer employer, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id", employer.getId());
        jsonGenerator.writeStringField("name", employer.getName());
        jsonGenerator.writeStringField("description", employer.getDescription());

        // Serialize address
        Address address = employer.getAddress();
        jsonGenerator.writeObjectFieldStart("address");
        jsonGenerator.writeStringField("street", address.getStreet());
        jsonGenerator.writeStringField("city", address.getCity());
        jsonGenerator.writeStringField("state", address.getState());
        jsonGenerator.writeStringField("zip", address.getZip());
        jsonGenerator.writeEndObject();

        // Serialize employees using ShallowEmployeeSerializer
        ShallowEmployeeSerializer shallowEmployeeSerializer = new ShallowEmployeeSerializer();
        jsonGenerator.writeArrayFieldStart("employees");
        List<Employee> employees = employer.getEmployees();
        if(employees != null) {
            for (Employee employee : employees) {
                shallowEmployeeSerializer.serialize(employee, jsonGenerator, serializerProvider);
            }
        }
        jsonGenerator.writeEndArray();

        jsonGenerator.writeEndObject();
    }
}
