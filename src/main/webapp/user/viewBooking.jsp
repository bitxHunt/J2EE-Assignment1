<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="models.transaction.Transaction"%>
<%@ page import="models.service.Service"%>
<%@ page import="models.address.Address"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="java.time.LocalDateTime"%>

<!DOCTYPE html>
<html lang="en" data-theme="dark">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Bookings - CleanX</title>
    <link href="https://cdn.jsdelivr.net/npm/daisyui@4.12.14/dist/full.min.css" rel="stylesheet" type="text/css" />
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</head>
<body class="bg-base-300 min-h-screen">
    <!-- Navbar -->
    <div class="navbar bg-base-100 shadow-xl">
        <div class="flex-1">
            <a href="${pageContext.request.contextPath}/" class="btn btn-ghost text-xl">CleanX</a>
        </div>
        <div class="flex-none">
            <div class="dropdown dropdown-end">
                <div tabindex="0" role="button" class="btn btn-ghost btn-circle avatar">
                    <div class="w-10 rounded-full ring ring-primary ring-offset-base-100 ring-offset-2">
                        <img alt="Profile" src="${pageContext.request.contextPath}/assets/avatar.jpg" />
                    </div>
                </div>
                <ul class="mt-3 z-[1] p-2 shadow menu menu-sm dropdown-content bg-base-100 rounded-box w-52">
                    <li><a href="${pageContext.request.contextPath}/profile">Profile</a></li>
                    <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
                </ul>
            </div>
        </div>
    </div>

    <div class="container mx-auto p-6 max-w-5xl">
        <!-- Header Section -->
        <div class="flex justify-between items-center mb-6">
            <h1 class="text-3xl font-bold">My Bookings</h1>
            <a href="${pageContext.request.contextPath}/book" class="btn btn-primary gap-2">
                <span class="material-symbols-outlined">add</span>
                Book New Service
            </a>
        </div>

        <!-- Booking Cards -->
        <div class="space-y-6">
            <%
            ArrayList<Transaction> transactions = (ArrayList<Transaction>) request.getAttribute("transactions");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
            
            if(transactions != null && !transactions.isEmpty()) {
                for(Transaction transaction : transactions) {
            %>
            <div class="card bg-base-200 shadow-xl transition-all hover:shadow-2xl">
                <div class="flex flex-col md:flex-row">
                    <!-- Image Section -->
                    <figure class="w-full md:w-48 relative">
                        <img src="<%=transaction.getBundle_img() != null ? transaction.getBundle_img() : transaction.getServices().get(0).getImageUrl()%>" 
                             alt="Service Image"
                             class="h-48 md:h-full w-full object-cover" />
                        <%
                        String statusClass = "";
                        String statusText = transaction.getStatus();
                        switch(statusText) {
                            case "PENDING":
                                statusClass = "badge-warning";
                                break;
                            case "IN_PROGRESS":
                                statusClass = "badge-info";
                                break;
                            case "COMPLETED":
                                statusClass = "badge-success";
                                break;
                            case "CANCELLED":
                                statusClass = "badge-error";
                                break;
                            default:
                                statusClass = "badge-ghost";
                        }
                        %>
                        <div class="absolute top-2 right-2">
                            <div class="badge <%=statusClass%> gap-2 text-xs">
                                <span class="material-symbols-outlined text-sm">circle</span>
                                <%=statusText%>
                            </div>
                        </div>
                    </figure>

                    <!-- Content Section -->
                    <div class="card-body">
                        <div class="flex flex-col gap-4">
                            <!-- Service Info -->
                            <div>
                                <h2 class="card-title text-primary">
                                    <%=transaction.getBundleName() != null ? transaction.getBundleName() : "Custom Services"%>
                                </h2>
                                <div class="flex flex-wrap gap-4 mt-2">
                                    <div class="flex items-center gap-2 text-sm opacity-75">
                                        <span class="material-symbols-outlined">event</span>
                                        <%=transaction.getStartDate().format(dateFormatter)%>
                                    </div>
                                    <div class="flex items-center gap-2 text-sm opacity-75">
                                        <span class="material-symbols-outlined">schedule</span>
                                        <%=transaction.getPaidDate() != null ? transaction.getPaidDate().format(timeFormatter) : "Time not set"%>
                                    </div>
                                </div>
                            </div>

                            <!-- Services List -->
                            <div class="bg-base-100 rounded-box p-4">
                                <div class="text-sm font-medium mb-2">Ordered Services:</div>
                                <div class="divide-y divide-base-content/10">
                                    <% 
                                    ArrayList<Service> services = transaction.getServices();
                                    if(services != null && !services.isEmpty()) {
                                        for(Service service : services) {
                                    %>
                                    <div class="flex items-center justify-between py-2">
                                        <div class="flex items-center gap-3">
                                            <span class="material-symbols-outlined text-primary">cleaning_services</span>
                                            <span><%=service.getServiceName()%></span>
                                        </div>
                                        <span class="text-sm opacity-75">$<%=String.format("%.2f", service.getPrice())%></span>
                                    </div>
                                    <%
                                        }
                                    } else {
                                    %>
                                    <div class="text-center py-2 text-base-content/70">No services found</div>
                                    <%
                                    }
                                    %>
                                </div>
                            </div>

                            <!-- Price and Location -->
                            <div class="flex flex-wrap gap-4">
                                <div class="flex items-center gap-2 bg-warning/10 text-warning rounded-lg px-3 py-2">
                                    <span class="material-symbols-outlined">paid</span>
                                    <span class="font-medium">$<%=String.format("%.2f", transaction.getSubTotal())%></span>
                                </div>
                                <% if(transaction.getDiscount() > 0) { %>
                                <div class="flex items-center gap-2 bg-success/10 text-success rounded-lg px-3 py-2">
                                    <span class="material-symbols-outlined">
                                        sell
                                        </span>
                                    <span class="font-medium"><%=transaction.getDiscount()%>% OFF</span>
                                </div>
                                <% } %>
                                <div class="flex items-center gap-2 bg-error/10 text-error rounded-lg px-3 py-2">
                                    <span class="material-symbols-outlined">location_on</span>
                                    <span><%=transaction.getAddress().getPostalCode()%></span>
                                </div>
                            </div>

                            <!-- Action Buttons -->
                            <div class="card-actions justify-end gap-2">
                                <% if(transaction.getStatus().equals("PENDING")) { %>
                                <button onclick="document.getElementById('cancel_modal_<%=transaction.getId()%>').showModal()" 
                                        class="btn btn-error btn-sm gap-2">
                                    <span class="material-symbols-outlined">cancel</span>
                                    Cancel Booking
                                </button>
                                
                                <!-- Cancel Confirmation Modal -->
                                <dialog id="cancel_modal_<%=transaction.getId()%>" class="modal modal-bottom sm:modal-middle">
                                    <div class="modal-box">
                                        <h3 class="font-bold text-lg">Cancel Booking</h3>
                                        <p class="py-4">Are you sure you want to cancel this booking? This action cannot be undone.</p>
                                        <div class="modal-action">
                                            <form method="dialog">
                                                <button class="btn btn-ghost btn-sm">Close</button>
                                            </form>
                                            <a href="${pageContext.request.contextPath}/booking/cancel?id=<%=transaction.getId()%>" 
                                               class="btn btn-error btn-sm">
                                                Confirm Cancel
                                            </a>
                                        </div>
                                    </div>
                                </dialog>
                                <% } %>

                                <a href="${pageContext.request.contextPath}/booking/details?id=<%=transaction.getId()%>"
                                   class="btn btn-primary btn-sm gap-2">
                                    <span class="material-symbols-outlined">visibility</span>
                                    View Details
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <%
                }
            } else {
            %>
            <!-- Empty State -->
            <div class="card bg-base-200 shadow-xl text-center p-12">
                <div class="flex flex-col items-center gap-4">
                    <div class="p-4 bg-base-100 rounded-full">
                        <span class="material-symbols-outlined text-6xl opacity-50">calendar_month</span>
                    </div>
                    <div>
                        <h3 class="font-bold text-xl mb-2">No Bookings Yet</h3>
                        <p class="text-base-content/70 mb-6">Start your cleaning journey with us today!</p>
                        <a href="${pageContext.request.contextPath}/book" 
                           class="btn btn-primary gap-2">
                            <span class="material-symbols-outlined">add</span>
                            Book Your First Service
                        </a>
                    </div>
                </div>
            </div>
            <%
            }
            %>
        </div>
    </div>
</body>
</html>