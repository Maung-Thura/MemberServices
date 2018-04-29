package member.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class Iso8601DateSerializer extends StdSerializer<Long> {

	private static final long serialVersionUID = 2599928190579758260L;

	private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmXXX");

	public Iso8601DateSerializer() {
		this(null);
	}

	public Iso8601DateSerializer(Class<Long> c) {
		super(c);
	}

	@Override
	public void serialize(Long unixTimestamp, JsonGenerator generator, SerializerProvider provider) throws IOException {
		generator.writeString(
				unixTimestamp != null && unixTimestamp > 0l ? formatter.format(unixTimestamp * 1000l) : "");
	}

}
