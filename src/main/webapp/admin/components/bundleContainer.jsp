<%@page import="models.bundle.Bundle"%>
<%@page import="java.util.ArrayList"%>
<%@page import="models.service.Service"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Bundle Container</title>
</head>
<body>
<!-- Bundles Section -->
<div id="bundles"
			class="hidden grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
			<%
			ArrayList<Bundle> bundles = (ArrayList<Bundle>) request.getAttribute("bundles");
			if (bundles != null && !bundles.isEmpty()) {
				for (Bundle bundle : bundles) {
			%>
			<div
				class="card bg-base-200 shadow-xl <%=!bundle.getIsActive() ? "opacity-40" : ""%>">
				<figure class="px-6 pt-6">
					<img
						src="<%=bundle.getImageUrl() != null
		? bundle.getImageUrl()
		: "https://res.cloudinary.com/dnaulhgz8/image/upload/v1732267743/default_cleaning_image_fz3izs.webp"%>"
						alt="Bundle Image" class="rounded-xl object-cover h-48 w-full" />
				</figure>
				<div class="card-body">
					<div class="flex justify-between items-center">
						<h2 class="card-title"><%=bundle.getBundleName()%></h2>
						<div class="badge badge-secondary"><%=bundle.getDiscountPercent()%>%
							OFF
						</div>
					</div>

					<!-- Price Section -->
					<div class="flex items-end gap-2 mt-2">
						<span class="text-xl font-bold text-primary"> $<%=String.format("%.2f", bundle.getDiscountedPrice())%>
						</span> <span class="text-sm line-through opacity-50"> $<%=String.format("%.2f", bundle.getOriginalPrice())%>
						</span>
					</div>

					<div class="divider">Included Services</div>
					<ul class="list-disc list-inside text-sm">
						<%
						for (Service service : bundle.getServices()) {
						%>
						<li><%=service.getServiceName()%> - $<%=String.format("%.2f", service.getPrice())%></li>
						<%
						}
						%>
					</ul>
					<div class="card-actions justify-between items-center mt-4">
						<div class="flex items-center gap-2">
							<span class="text-sm font-medium">Status:</span> <span
								class="px-3 py-1 rounded-full text-sm font-semibold <%=bundle.getIsActive() ? "bg-green-100 text-green-800" : "bg-red-100 text-red-800"%>">
								<%=bundle.getIsActive() ? "Active" : "Inactive"%>
							</span>
						</div>
						<button class="btn btn-primary"
							onclick="window.location.href='${pageContext.request.contextPath}/admin/edit-bundle/<%=bundle.getBundleId()%>'">
							Edit</button>
					</div>

				</div>
			</div>
			<%
			}
			} else {
			%>
			<div class="col-span-full text-center py-8">
				<div class="max-w-md mx-auto bg-base-200 rounded-xl shadow-lg p-6">
					<h3 class="text-lg font-semibold mb-2">No Bundles Available</h3>
					<p class="text-sm opacity-70 mb-4">There are currently no
						bundles. Click 'Create New' to add a bundle.</p>
					<button
						onclick="window.location='${pageContext.request.contextPath}/admin/createForm?tab=bundle'"
						class="btn btn-primary">Create Bundle</button>
				</div>
			</div>
			<%
			}
			%>
		</div>

</body>
</html>