<%@ page import="java.time.LocalDate"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="java.util.*"%>
<%@ page import="models.timeSlot.TimeSlot"%>

<%
ArrayList<LocalDate> weekDates = (ArrayList<LocalDate>) request.getAttribute("weekDates");
ArrayList<TimeSlot> timeSlots = (ArrayList<TimeSlot>) request.getAttribute("timeSlots");
DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("E");
DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd");
%>

<!DOCTYPE html>
<html data-theme="dark">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>Book Your Session</title>
<link href="https://cdn.jsdelivr.net/npm/daisyui@3.9.4/dist/full.css"
	rel="stylesheet" />
<script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="min-h-screen bg-gradient-to-b from-base-300 to-base-200">
	<%
	Integer userId = (Integer) session.getAttribute("userId");
	if (userId != null) {
	%>
	<%@ include file="./components/header.jsp"%>
	<%
	} else {
	%>
	<%@ include file="../../public/components/header.jsp"%>
	<%
	}
	%>

	<main class="container mx-auto px-4 py-8">
		<div class="max-w-4xl mx-auto">
			<!-- Title Section -->
			<div class="text-center mb-8">
				<h1
					class="text-4xl font-bold bg-gradient-to-r from-purple-500 to-pink-500 bg-clip-text text-transparent">Book
					Your Session</h1>
				<p class="text-base-content/70 mt-2">Choose your preferred date
					and time slot</p>
			</div>

			<!-- Progress Steps -->
			<div class="mb-12">
				<ul class="steps steps-horizontal w-full">
					<li class="step step-primary font-medium">Select Time</li>
					<li class="step font-medium">Choose Address</li>
					<li class="step font-medium">Service Info</li>
					<li class="step font-medium">Review</li>
				</ul>
			</div>

			<!-- Date Selection -->
			<form id="dateForm" method="get"
				action="<%=request.getContextPath()%>/book"
				class="w-full mb-8 bg-base-100 p-6 rounded-lg shadow-xl">
				<div
					class="flex justify-between items-center border-b border-base-300 pb-4 mb-6">
					<%
					for (LocalDate date : weekDates) {
					%>
					<div class="flex flex-col items-center">
						<span class="text-sm text-base-content/70 mb-2"><%=date.format(dayFormatter)%></span>
						<div class="relative">
							<input type="radio" name="date" value="<%=date%>"
								class="date-radio absolute opacity-0 w-full h-full cursor-pointer"
								onchange="document.getElementById('dateForm').submit();" />
							<div
								class="date-label px-4 py-2 rounded-lg border-2 border-transparent hover:border-purple-500/50 transition-all">
								<span class="text-lg font-medium"><%=date.format(dateFormatter)%></span>
							</div>
						</div>
					</div>
					<%
					}
					%>
				</div>
			</form>

			<!-- Time Slots Grid -->
			<form method="post"
				action="<%=request.getContextPath()%>/book">
				<input type="hidden" name="date"
					value="<%=request.getParameter("date")%>">
				<div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
					<%
					int currentSlot = 0;
					String[] periods = { "Morning", "Afternoon", "Evening" };
					for (String period : periods) {
					%>
					<div
						class="card bg-base-100 shadow-xl border border-base-300 hover:border-primary/20 transition-all duration-300">
						<div class="card-body">
							<div class="flex items-center gap-3 mb-6">
								<div class="p-2 rounded-lg bg-primary/10">
									<svg xmlns="http://www.w3.org/2000/svg"
										class="w-6 h-6 text-primary" fill="none" viewBox="0 0 24 24"
										stroke="currentColor">
                                        <path stroke-linecap="round"
											stroke-linejoin="round" stroke-width="2"
											d="M12 3v1m0 16v1m9-9h-1M4 12H3m15.364 6.364l-.707-.707M6.343 6.343l-.707-.707m12.728 0l-.707.707M6.343 17.657l-.707.707" />
                                    </svg>
								</div>
								<h2 class="card-title text-xl text-primary"><%=period%></h2>
							</div>
							<div class="space-y-4">
								<%
								for (int i = 0; i < 2 && currentSlot < timeSlots.size(); i++, currentSlot++) {
									TimeSlot slot = timeSlots.get(currentSlot);
								%>
								<label
									class="flex justify-between items-center p-4 rounded-xl border border-base-300 hover:border-primary/20 hover:shadow-lg transition-all duration-300 cursor-pointer <%=slot.getCapacity() <= 0 ? "opacity-50" : ""%>">
									<div class="flex flex-col">
										<span class="text-lg font-medium"><%=slot.getStartTime()%></span>
										<span class="text-sm text-base-content/70"><%=slot.getCapacity()%>
											Slots Available</span>
									</div> 
									
									<% 
									
									if (slot.getCapacity() > 0) { 
									
									%> 
									
									<input type="radio" name="selected_slot"
									value="<%=slot.getId()%>"
									class="radio radio-primary" required /> 
									
									<%
									
 									} else {
 									
 									%> 
 									
 									<span class="badge badge-error gap-2"> <svg
											xmlns="http://www.w3.org/2000/svg" class="w-4 h-4"
											fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                            <path stroke-linecap="round"
												stroke-linejoin="round" stroke-width="2"
												d="M6 18L18 6M6 6l12 12" />
                                        </svg> Fully Booked
									</span> 
									
									<% 
									
 									}
 
 									%>
								</label>
								<%
								}
								%>
							</div>
						</div>
					</div>
					<%
					}
					%>
				</div>

				<!-- Continue Button -->
				<div class="flex justify-center mb-8">
					<button type="submit" class="btn btn-primary btn-lg gap-2">
						Continue to Address
						<svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5"
							viewBox="0 0 20 20" fill="currentColor">
                            <path fill-rule="evenodd"
								d="M10.293 3.293a1 1 0 011.414 0l6 6a1 1 0 010 1.414l-6 6a1 1 0 01-1.414-1.414L14.586 11H3a1 1 0 110-2h11.586l-4.293-4.293a1 1 0 010-1.414z"
								clip-rule="evenodd" />
                        </svg>
					</button>
				</div>
			</form>

			<!-- Legend -->
			<div
				class="flex flex-wrap gap-6 justify-center p-6 bg-base-100 rounded-box shadow-lg border border-base-300">
				<div class="flex items-center gap-3">
					<div class="w-4 h-4 rounded-full bg-primary"></div>
					<span class="text-base-content/80">Available</span>
				</div>
				<div class="flex items-center gap-3">
					<div class="w-4 h-4 rounded-full bg-error"></div>
					<span class="text-base-content/80">Fully Booked</span>
				</div>
			</div>
		</div>
	</main>

	<!-- Footer -->
	<%@ include file="components/footer.jsp"%>
</body>
</html>