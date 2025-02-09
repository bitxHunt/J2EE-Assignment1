<%-- 
    Name: Thiha Swan Htet
    Class: DIT/FT/2B/01
    Admin No: p2336671
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-theme="dark">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Setup Payment Method</title>
<link
	href="https://cdn.jsdelivr.net/npm/daisyui@4.12.23/dist/full.min.css"
	rel="stylesheet" type="text/css" />
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
<script src="https://js.stripe.com/v3/"></script>
</head>
<body class="min-h-screen bg-gradient-to-b from-base-300 to-base-200">
	<%@ include file="./components/header.jsp"%>

	<main class="container mx-auto px-4 py-8">
		<div class="max-w-2xl mx-auto">
			<h1
				class="text-4xl font-bold text-center mb-2 bg-gradient-to-r from-purple-500 to-pink-500 bg-clip-text text-transparent">
				Add Payment Method</h1>
			<p class="text-base-content/70 text-center mb-8">Please add your
				credit card details for backup payment</p>

			<div class="card bg-base-100 shadow-xl">
				<div class="card-body">
					<form id="payment-form" class="space-y-6">
						<div class="form-control">
							<label class="label"> <span class="label-text">Name
									on Card</span>
							</label> <input type="text" id="cardholderName"
								class="input input-bordered" required>
						</div>

						<div class="form-control">
							<label class="label"> <span class="label-text">Card
									Information</span>
							</label>
							<div id="card-element" class="p-4 border rounded bg-base-200"></div>
							<div id="card-errors" class="text-error text-sm mt-2"
								role="alert"></div>
						</div>

						<div class="form-control mt-6">
							<button type="submit" class="btn btn-primary">
								<span id="button-text">Save Payment Method</span> <span
									id="spinner" class="hidden">Processing...</span>
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</main>

	<script>
        // Initialize Stripe
        const stripe = Stripe('pk_test_51QZ3ArRpRtGdj3CoLdTCIJfXZUq5Uv8aGJqBqihGoZnFwgkuk4dqQ884Mfpt73SX7Kqz8hIIQ4birEHiQgcLSJom00MPkDmXIh');
        const elements = stripe.elements();
        
        // Create card Element
        const card = elements.create('card', {
            style: {
                base: {
                    color: '#ffffff',
                    fontSize: '16px',
                    '::placeholder': {
                        color: '#aab7c4'
                    }
                },
                invalid: {
                    color: '#fa755a',
                    iconColor: '#fa755a'
                }
            }
        });
        
        // Mount card Element
        card.mount('#card-element');
        
        // Handle form submission
        const form = document.getElementById('payment-form');
        const cardholderName = document.getElementById('cardholderName');
        
     // Add debugging to track the flow
        form.addEventListener('submit', async (event) => {
            event.preventDefault();
            const buttonText = document.getElementById('button-text');
            const spinner = document.getElementById('spinner');
            
            try {
                buttonText.classList.add('hidden');
                spinner.classList.remove('hidden');
                
                const {setupIntent, error} = await stripe.confirmCardSetup(
                    '<%=request.getAttribute("clientSecret")%>',
                    {
                        payment_method: {
                            card: card,
                            billing_details: {
                                name: cardholderName.value,
                                email: '<%=request.getAttribute("userEmail")%>'
                            }
                        }
                    }
                );
                
                if (error) {
                    console.error('Setup error:', error);
                    const errorElement = document.getElementById('card-errors');
                    errorElement.textContent = error.message;
                } else {
                    console.log('Setup success, payment method:', setupIntent.payment_method);
                    const response = await fetch('${pageContext.request.contextPath}/payment/setup', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded',
                        },
                        body: 'paymentMethodId=' + setupIntent.payment_method
                    });

                    if (response.ok) {
                        window.location.href = '${pageContext.request.contextPath}/book/review';
                    } else {
                        throw new Error('Server error');
                    }
                }
            } catch (error) {
                console.error('Processing error:', error);
                const errorElement = document.getElementById('card-errors');
                errorElement.textContent = error.message;
            } finally {
                buttonText.classList.remove('hidden');
                spinner.classList.add('hidden');
            }
        });

    </script>

	<%@ include file="components/footer.jsp"%>
</body>
</html>