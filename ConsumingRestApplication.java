@SpringBootApplication
public class ConsumingRestApplication {

	private static final Logger log = LoggerFactory.getLogger(ConsumingRestApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ConsumingRestApplication.class, args);
	}

	@Bean(name="example")
	public RestTemplate restTemplateExample() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
	    TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

	    SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom()
	                    .loadTrustMaterial(null, acceptingTrustStrategy)
	                    .build();
	    SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext,SSLConnectionSocketFactory,ALLOW_ALL_HOSTNAME_VERIFIER);
	    CloseableHttpClient httpClient = HttpClients.custom()
	                    .setSSLSocketFactory(csf)
	                    .build();
	    HttpComponentsClientHttpRequestFactory requestFactory =
	                    new HttpComponentsClientHttpRequestFactory();
	    requestFactory.setHttpClient(httpClient);
	    requestFactory.setConnectTimeout(5000);
	    requestFactory.setReadTimeout(5000);
	    RestTemplate restTemplate = new RestTemplate(requestFactory);
	    return restTemplate;
	}
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	@Profile("!test")
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			Quote quote = restTemplate.getForObject(
					"http://localhost:8080/api/random", Quote.class);
			log.info(quote.toString());
		};
	}
}
