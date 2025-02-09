<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html data-theme="dark">
<head>
    <title>Manage Bookings</title>
    <link href="https://cdn.jsdelivr.net/npm/daisyui@4.7.2/dist/full.min.css"
        rel="stylesheet" type="text/css" />
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="min-h-screen bg-base-200">
    <div class="p-8">
        <div class="flex justify-between items-center mb-6">
            <%@ include file="./components/adminNavbar.jsp"%>
        </div>
        <h2 class="text-2xl font-bold">Manage Bookings</h2>
        
        <!-- Error Messages -->
        <c:if test="${not empty error}">
            <div class="alert alert-error shadow-lg mt-4">
                <div>
                    <svg xmlns="http://www.w3.org/2000/svg"
                        class="stroke-current flex-shrink-0 h-6 w-6" fill="none"
                        viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" 
                            stroke-width="2"
                            d="M10 14l2-2m0 0l2-2m-2 2l-2-2m2 2l2 2m7-2a9 9 0 11-18 0 9 9 0 0118 0z" />
                    </svg>
                    <span>${error}</span>
                </div>
            </div>
        </c:if>

        <!-- Filter Form -->
        <div class="bg-base-100 p-4 rounded-lg shadow mb-4">
            <form method="GET" class="flex flex-wrap gap-4 items-end">
                <div class="form-control">
                    <label class="label">
                        <span class="label-text">Postal Code</span>
                    </label>
                    <input type="number" name="postalCode" value="${postalCode}"
                        placeholder="Enter postal code" 
                        class="input input-bordered w-full max-w-xs"
                        min="0" max="999999"/>
                </div>
                
                <div class="form-control">
                    <label class="label">
                        <span class="label-text">Start Date</span>
                    </label>
                    <input type="date" name="startDate" value="${startDate}"
                        class="input input-bordered w-full max-w-xs"/>
                </div>
                
                <div class="form-control">
                    <label class="label">
                        <span class="label-text">End Date</span>
                    </label>
                    <input type="date" name="endDate" value="${endDate}"
                        class="input input-bordered w-full max-w-xs"/>
                </div>

                <div class="form-control">
                    <button type="submit" class="btn btn-primary">Filter</button>
                    <a href="?page=1" class="btn btn-ghost ml-2">Reset</a>
                </div>
            </form>
        </div>

        <!-- Bookings Table -->
        <div class="overflow-x-auto bg-base-100 rounded-lg shadow">
            <table class="table table-zebra">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Status</th>
                        <th>Scheduled Date</th>
                        <th>Booked At</th>
                        <th>Time Slot</th>
                        <th>Address</th>
                        <th>Customer Phone</th>
                        <th>Assigned Staff</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="booking" items="${bookings}">
                        <tr>
                            <td>${booking.bookingId}</td>
                            <td>
                                <div class="badge ${booking.status == 'PENDING' ? 'badge-warning' : 
                                                  booking.status == 'CONFIRMED' ? 'badge-info' :
                                                  booking.status == 'COMPLETED' ? 'badge-success' : 
                                                  'badge-error'}">
                                    ${booking.status}
                                </div>
                            </td>
                            <td class="scheduled-date">${booking.scheduledDate}</td>
                            <td class="booked-at">${booking.bookedAt}</td>
                            <td>${booking.startTime}-${booking.endTime}</td>
                            <td>
                                <div class="max-w-xs truncate">${booking.street},
                                    #${booking.unit}, ${booking.postalCode}</div>
                            </td>
                            <td>${booking.customerPhone}</td>
                            <td><span
                                class="${booking.assignedStaffEmail != null ? 'text-success' : 'text-error'}">
                                    ${booking.assignedStaffEmail != null ? booking.assignedStaffEmail : 'Not Assigned'}
                            </span></td>
                            <td><c:if test="${!booking.assigned}">
                                    <button onclick="openAssignModal(${booking.bookingId})"
                                        class="btn btn-primary btn-sm">Assign Staff</button>
                                </c:if> <c:if test="${booking.assigned}">
                                    <button onclick="confirmCancel(${booking.bookingId})"
                                        class="btn btn-error btn-sm">Cancel</button>
                                </c:if></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>

        <!-- Pagination -->
        <div class="flex justify-center mt-4 space-x-2">
            <c:if test="${currentPage > 1}">
                <a href="?page=${currentPage - 1}&postalCode=${postalCode}&startDate=${startDate}&endDate=${endDate}" 
                   class="btn btn-sm">Previous</a>
            </c:if>

            <c:forEach begin="1" end="${totalPages}" var="pageNum">
                <a href="?page=${pageNum}&postalCode=${postalCode}&startDate=${startDate}&endDate=${endDate}"
                   class="btn btn-sm ${pageNum == currentPage ? 'btn-active' : ''}">
                    ${pageNum}
                </a>
            </c:forEach>

            <c:if test="${currentPage < totalPages}">
                <a href="?page=${currentPage + 1}&postalCode=${postalCode}&startDate=${startDate}&endDate=${endDate}" 
                   class="btn btn-sm">Next</a>
            </c:if>
        </div>

        <!-- Modal for Staff Assignment -->
        <dialog id="assignStaffModal" class="modal">
            <div class="modal-box">
                <h3 class="font-bold text-lg mb-4">Assign Staff to Booking</h3>
                <form method="POST"
                    action="${pageContext.request.contextPath}/admin/manage-bookings"
                    class="space-y-4" onsubmit="return validateForm()">
                    <input type="hidden" id="bookingId" name="bookingId">

                    <div class="form-control">
                        <label class="label">
                            <span class="label-text">Select Staff</span>
                        </label>
                        <select class="select select-bordered w-full" name="staffId" required>
                            <option value="" disabled selected>Choose a staff member</option>
                            <c:forEach var="staff" items="${staffList}">
                                <option value="${staff.id}">${staff.firstName}
                                    ${staff.lastName} (${staff.email})</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="modal-action">
                        <button type="submit" class="btn btn-primary">Assign</button>
                        <button type="button" class="btn" onclick="closeAssignModal()">Cancel</button>
                    </div>
                </form>
            </div>
            <form method="dialog" class="modal-backdrop">
                <button>close</button>
            </form>
        </dialog>

        <script>
            document.addEventListener('DOMContentLoaded', function() {
                document.querySelectorAll('.scheduled-date').forEach(element => {
                    const date = new Date(element.textContent);
                    element.textContent = date.toLocaleDateString('en-GB');
                });

                document.querySelectorAll('.booked-at').forEach(element => {
                    const date = new Date(element.textContent);
                    element.textContent = date.toLocaleDateString('en-GB') + ' ' + 
                                        date.toLocaleTimeString('en-GB', {
                                            hour: '2-digit',
                                            minute: '2-digit'
                                        });
                });
            });

            function openAssignModal(bookingId) {
                document.getElementById('bookingId').value = bookingId;
                document.getElementById('assignStaffModal').showModal();
            }

            function closeAssignModal() {
                document.getElementById('assignStaffModal').close();
            }

            function validateForm() {
                const staffId = document.querySelector('select[name="staffId"]').value;
                if (!staffId) {
                    alert('Please select a staff member');
                    return false;
                }
                return true;
            }
            
            function confirmCancel(bookingId) {
                if (confirm('Are you sure you want to cancel this booking assignment?')) {
                    window.location.href = '${pageContext.request.contextPath}/admin/manage-bookings?action=cancel&bookingId=' + bookingId;
                }
            }
        </script>
    </div>
</body>
</html>