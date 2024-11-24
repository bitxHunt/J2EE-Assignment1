<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" data-theme="dark">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>About Us - CleanX</title>
<link
	href="https://cdn.jsdelivr.net/npm/daisyui@4.12.14/dist/full.min.css"
	rel="stylesheet" type="text/css" />
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</head>
<body>
	<!-- Header -->
	<%@ include file="components/header.jsp"%>
	<!-- Hero Section -->
	<div class="hero min-h-[60vh] bg-base-200">
		<div class="hero-content text-center">
			<div class="max-w-3xl">
				<h1 class="text-5xl font-bold mb-8">About CleanX</h1>
				<p class="text-xl mb-6">Your Trusted Partner in Professional
					Cleaning Services</p>
			</div>
		</div>
	</div>

	<!-- Our Story Section -->
	<section class="py-16 bg-base-100">
		<div class="container mx-auto px-4">
			<div class="grid grid-cols-1 md:grid-cols-2 gap-12 items-center">
				<div>
					<h2 class="text-3xl font-bold mb-6">Our Story</h2>
					<p class="mb-4">Founded in 2014, CleanX began with a simple
						mission: to provide professional, reliable, and eco-friendly
						cleaning services to our community. What started as a small team
						of dedicated cleaners has grown into a trusted cleaning service
						provider.</p>
					<p class="mb-4">Our journey has been marked by continuous
						improvement, adoption of best practices, and an unwavering
						commitment to customer satisfaction. Today, we serve hundreds of
						satisfied customers across residential and commercial properties.</p>
				</div>
				<div class="grid grid-cols-2 gap-4">
					<div class="stat bg-base-200 rounded-box">
						<div class="stat-title">Happy Clients</div>
						<div class="stat-value">1,200+</div>
					</div>
					<div class="stat bg-base-200 rounded-box">
						<div class="stat-title">Team Members</div>
						<div class="stat-value">50+</div>
					</div>
					<div class="stat bg-base-200 rounded-box">
						<div class="stat-title">Years of Service</div>
						<div class="stat-value">10+</div>
					</div>
					<div class="stat bg-base-200 rounded-box">
						<div class="stat-title">Projects Completed</div>
						<div class="stat-value">15K+</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- Values Section -->
	<section class="py-16 bg-base-200">
		<div class="container mx-auto px-4">
			<h2 class="text-3xl font-bold text-center mb-12">Our Values</h2>
			<div class="grid grid-cols-1 md:grid-cols-3 gap-8">
				<div class="card bg-base-100">
					<div class="card-body items-center text-center">
						<span class="material-symbols-outlined text-4xl text-primary">verified_user</span>
						<h3 class="card-title">Quality</h3>
						<p>We maintain the highest standards in cleaning services,
							using professional-grade equipment and eco-friendly products.</p>
					</div>
				</div>
				<div class="card bg-base-100">
					<div class="card-body items-center text-center">
						<span class="material-symbols-outlined text-4xl text-primary">diversity_3</span>
						<h3 class="card-title">Reliability</h3>
						<p>Our team is committed to punctuality and consistency,
							ensuring you can always count on us.</p>
					</div>
				</div>
				<div class="card bg-base-100">
					<div class="card-body items-center text-center">
						<span class="material-symbols-outlined text-4xl text-primary">eco</span>
						<h3 class="card-title">Sustainability</h3>
						<p>We prioritize environmental responsibility by using
							eco-friendly cleaning products and sustainable practices.</p>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- Team Section -->
	<section class="py-16 bg-base-100">
		<div class="container mx-auto px-4">
			<h2 class="text-3xl font-bold text-center mb-12">Our Leadership
				Team</h2>
			<div class="max-w-4xl mx-auto">
				<div
					class="grid grid-cols-1 md:grid-cols-2 gap-8 justify-items-center">
					<!-- First Team Member -->
					<div
						class="card bg-base-200 w-full max-w-sm hover:shadow-lg transition-all duration-300">
						<div class="card-body items-center text-center p-8">
							<div class="avatar placeholder mb-6">
								<div class="bg-neutral text-neutral-content rounded-full w-32">
									<span class="text-4xl">TSH</span>
								</div>
							</div>
							<h3 class="card-title text-2xl mb-2">Thiha Swan Htet</h3>
							<p class="text-primary font-semibold">Founder & CEO</p>
							<div class="divider"></div>
							<p class="text-base-content/70">Leading the vision and growth
								of CleanX with innovation and excellence</p>
						</div>
					</div>

					<!-- Second Team Member -->
					<div
						class="card bg-base-200 w-full max-w-sm hover:shadow-lg transition-all duration-300">
						<div class="card-body items-center text-center p-8">
							<div class="avatar placeholder mb-6">
								<div class="bg-neutral text-neutral-content rounded-full w-32">
									<span class="text-4xl">SZA</span>
								</div>
							</div>
							<h3 class="card-title text-2xl mb-2">Soe Zaw Aung</h3>
							<p class="text-primary font-semibold">Co-Founder</p>
							<div class="divider"></div>
							<p class="text-base-content/70">Driving operational
								excellence and customer satisfaction</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- CTA Section -->
	<section class="py-16 bg-base-200">
		<div class="container mx-auto px-4 text-center">
			<h2 class="text-3xl font-bold mb-6">Ready to Experience Our
				Service?</h2>
			<p class="mb-8 max-w-2xl mx-auto">Join our growing family of
				satisfied customers and experience the CleanX difference.</p>
			<div class="flex justify-center gap-4">
				<a href="${pageContext.request.contextPath}/book"
					class="btn btn-primary">Book Now</a>
			</div>
		</div>
	</section>

	<!-- Footer -->
	<%@ include file="components/footer.jsp"%>
</body>
</html>