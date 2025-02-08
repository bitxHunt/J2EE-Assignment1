package util;

import com.stripe.exception.ApiConnectionException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.StripeException;
import com.stripe.exception.ApiException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.SetupIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.SetupIntentCreateParams;

import java.util.HashMap;
import java.util.Map;

import com.stripe.Stripe;

public class StripeConnection {

	// Set the stripe API Key in the constructor
	public StripeConnection() {
		Stripe.apiKey = SecretsConfig.getStripeSecretKey();
	}

	public String createSetupIntent() throws StripeException {
		SetupIntentCreateParams params = SetupIntentCreateParams.builder().addPaymentMethodType("card").build();

		SetupIntent setupIntent = SetupIntent.create(params);

		System.out.println("Stripe API Key: " + SecretsConfig.getStripeSecretKey());
		return setupIntent.getClientSecret();
	}

	public String createCustomerWithPaymentMethod(String email, String paymentMethodId) throws StripeException {
		// Create Customer
		CustomerCreateParams customerParams = CustomerCreateParams.builder().setEmail(email)
				.setPaymentMethod(paymentMethodId).build();

		Customer customer = Customer.create(customerParams);
		return customer.getId();
	}

	public String createPayNowQR(float amount, String reference) throws StripeException {
		String qrUrl = "";
		try {
			// Setup basic payment parameters
			Map<String, Object> params = new HashMap<>();
			params.put("amount", (long) (amount * 100)); // Convert to cents
			params.put("currency", "sgd");
			params.put("confirm", true);
			params.put("payment_method_types", new String[] { "paynow" });

			// Add payment method data for PayNow
			Map<String, Object> paymentMethodData = new HashMap<>();
			paymentMethodData.put("type", "paynow");
			params.put("payment_method_data", paymentMethodData);

			// Add metadata for reference
			Map<String, String> metadata = new HashMap<>();
			metadata.put("reference", reference);
			params.put("metadata", metadata);

			// Create and confirm PaymentIntent
			PaymentIntent payment = PaymentIntent.create(params);
			System.out.println("Payment Intent Status: " + payment.getStatus());

			// Get QR code URL using the proper NextAction object
			PaymentIntent.NextAction nextAction = payment.getNextAction();
			if (nextAction != null && nextAction.getPaynowDisplayQrCode() != null) {
				qrUrl = nextAction.getPaynowDisplayQrCode().getImageUrlPng();
				System.out.println("QR Code URL generated: " + qrUrl);
			} else {
				System.out.println(
						"Next action is null or QR code not available. Payment Intent Status: " + payment.getStatus());
			}

		} catch (StripeException e) {
			System.out.println("Stripe error: " + e.getMessage());
			System.out.println("Error code: " + e.getCode());
			System.out.println("Status code: " + e.getStatusCode());
			throw e;
		} catch (Exception e) {
			System.out.println("Unexpected error: " + e.getMessage());
			e.printStackTrace();
		}

		return qrUrl;
	}

	// Create a product in stripe dashboard by the stripe API
	public void createProduct(String name, String description, boolean isActive, Integer price, String imageUrl) {
		String systemMessage = "Running Stripe Product Creation Function";
		String errMessage = "";

		try {
			// Log the current running function
			System.out.println(systemMessage);
			System.out.println("Secret Key: " + SecretsConfig.getStripeSecretKey());

			// Call stripe api to create product
			ProductCreateParams params = ProductCreateParams.builder().setName(name).setDescription(description)
					.setActive(isActive).setDefaultPriceData(ProductCreateParams.DefaultPriceData.builder()
							.setUnitAmount(price.longValue()).setCurrency("sgd").build())
					.addExpand("default_price").addImage(imageUrl).build();

			// Log out the result
			Product product = Product.create(params);
			System.out.println("Product created successfully: " + product.toJson());
			System.out.println("Product ID: " + product.getId());

			// Error Handling
		} catch (AuthenticationException e) {
			errMessage = "Authentication Error Running Stripe Product Creation Function.";
			System.out.println(errMessage);
			e.printStackTrace();
		} catch (ApiConnectionException e) {
			errMessage = "Api Connection Error Running Stripe Product Creation Function.";
			System.out.println(errMessage);
			e.printStackTrace();
		} catch (ApiException e) {
			errMessage = "Api Exception Error Running Stripe Product Creation Function.";
			System.out.println(errMessage);
			e.printStackTrace();
		} catch (StripeException e) {
			errMessage = "Stripe Error Running Stripe Product Creation Function.";
			System.out.println(errMessage);
			e.printStackTrace();
		} catch (Exception e) {
			errMessage = "General Error Running Stripe Product Creation Function.";
			System.out.println(errMessage);
			e.printStackTrace();
		}
	}

	public void createPrice() {
		String systemMessage = "Running Stripe Price Creation Function";
		String errMessage = "";

		try {
			// Log the current running function
			System.out.println(systemMessage);

			// Call stripe api to create price
			PriceCreateParams params = PriceCreateParams.builder().setCurrency("sgd").setUnitAmount(1000L)
					.setRecurring(PriceCreateParams.Recurring.builder()
							.setInterval(PriceCreateParams.Recurring.Interval.MONTH).build())
					.setProductData(PriceCreateParams.ProductData.builder().setName("Gold Plan").build()).build();

			// Log out the result
			Price price = Price.create(params);
			System.out.println("Product created successfully: " + price.getId());

			// Error Handling
		} catch (AuthenticationException e) {
			errMessage = "Authentication Error Running Stripe Product Creation Function.";
			System.out.println(errMessage);
			e.printStackTrace();
		} catch (ApiConnectionException e) {
			errMessage = "Api Connection Error Running Stripe Product Creation Function.";
			System.out.println(errMessage);
			e.printStackTrace();
		} catch (ApiException e) {
			errMessage = "Api Exception Error Running Stripe Product Creation Function.";
			System.out.println(errMessage);
			e.printStackTrace();
		} catch (StripeException e) {
			errMessage = "Stripe Error Running Stripe Product Creation Function.";
			System.out.println(errMessage);
			e.printStackTrace();
		} catch (Exception e) {
			errMessage = "General Error Running Stripe Product Creation Function.";
			System.out.println(errMessage);
			e.printStackTrace();
		}
	}
}