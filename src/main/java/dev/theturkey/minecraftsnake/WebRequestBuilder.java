package dev.theturkey.minecraftsnake;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebRequestBuilder
{
	private String url;
	private String requestType;
	private String body;
	private Map<String, String> headers;
	private List<URLProp> urlProps;

	/**
	 * Initiates a web request to later be executed from the given url as a GET Request
	 *
	 * @param url to make the request to
	 */
	public WebRequestBuilder(String url)
	{
		this(url, "GET");
	}

	/**
	 * Initiates a web request to later be executed from the given url and request type
	 *
	 * @param url         to make the request to
	 * @param requestType of web request to make
	 */
	public WebRequestBuilder(String url, String requestType)
	{
		this.url = url;
		setRequestType(requestType);
		this.headers = new HashMap<>();
		this.urlProps = new ArrayList<>();
	}

	/**
	 * Set the request type of this web request
	 *
	 * @param requestType to be set to
	 */
	public void setRequestType(String requestType)
	{
		this.requestType = requestType.toUpperCase();
	}


	/**
	 * Adds a header property to the web request being constructed
	 *
	 * @param key   of the header property
	 * @param value of the header property
	 */
	public void addHeaderProp(String key, String value)
	{
		headers.put(key, value);
	}

	/**
	 * Adds a url property to the web request being constructed
	 *
	 * @param key   of the url property
	 * @param value of the url property
	 */
	public void addURLProp(String key, String value)
	{
		this.urlProps.add(new URLProp(key, value));
	}

	/**
	 * Gets the URL that this Builder currently holds
	 *
	 * @return
	 */
	public String getURL()
	{
		StringBuilder builder = new StringBuilder(url);

		if(urlProps.size() > 0)
			builder.append("?");

		for(URLProp property : urlProps)
		{
			builder.append(property.key);
			builder.append("=");
			builder.append(property.value);
			builder.append("&");
		}
		builder.deleteCharAt(builder.length() - 1);
		return builder.toString();
	}

	/**
	 * Gets the body content that the builder currently has
	 *
	 * @return
	 */
	public String getBody()
	{
		return this.body;
	}

	/**
	 * Sets the body content that the builder currently has
	 *
	 * @return
	 */
	public void setBody(String body)
	{
		this.body = body;
	}

	/**
	 * Executes the currently constructed web request and returns the result
	 *
	 * @return result of the web request
	 * @throws IOException
	 */
	public String executeRequest() throws IOException
	{
		System.out.println(getURL());
		HttpURLConnection con = (HttpURLConnection) new URL(getURL()).openConnection();
		con.setRequestMethod(requestType);
		con.setConnectTimeout(15000);
		con.setReadTimeout(15000);
		con.setUseCaches(false);

		if(body != null && !body.isEmpty())
			con.setDoOutput(true);

		for(Map.Entry<String, String> headProp : headers.entrySet())
			con.setRequestProperty(headProp.getKey(), headProp.getValue());


		if(body != null && !body.isEmpty())
		{
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(getBody());
			wr.flush();
			wr.close();
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));

		StringBuilder buffer = new StringBuilder();
		String line;
		while((line = reader.readLine()) != null)
			buffer.append(line);

		return buffer.toString();
	}

	public static class URLProp
	{
		public String key;
		public String value;

		public URLProp(String key, String value)
		{
			this.key = key;
			this.value = value;
		}
	}
}
