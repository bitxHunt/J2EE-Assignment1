package util;

import com.stripe.exception.ApiConnectionException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.StripeException;
import com.stripe.exception.ApiException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;

import com.stripe.Stripe;

public class StripeConnection {

	// Set the stripe API Key in the constructor
	public StripeConnection() {
		Stripe.apiKey = StripeConfig.getStripeApiKey();
	}

	// Create a product in stripe dashboard by the stripe API
	public void createProduct(String name, String description, boolean isActive, Integer price, String imageUrl) {
		String systemMessage = "Running Stripe Product Creation Function";
		String errMessage = "";

		try {
			// Log the current running function
			System.out.println(systemMessage);
			System.out.println("Api Key: " + StripeConfig.getStripeApiKey());

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
