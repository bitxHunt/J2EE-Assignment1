<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-theme="dark">
<head>
    <meta charset="UTF-8">
    <title>400 - Bad Request</title>
    <link href="https://cdn.jsdelivr.net/npm/daisyui@3.9.4/dist/full.css" rel="stylesheet">
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</head>
<body class="min-h-screen bg-base-100 flex items-center justify-center">
    <div class="max-w-2xl mx-auto px-4 flex flex-col items-center text-center">
        <!-- 400 Number -->
        <div class="text-[150px] font-bold bg-gradient-to-r from-primary to-secondary bg-clip-text text-transparent">
            400
        </div>

        <!-- Icon -->
        <span class="material-symbols-outlined text-8xl text-error mb-8">
            warning
        </span>

        <!-- Message -->
        <h1 class="text-4xl font-bold mb-4 text-white">Bad Request</h1>
        <p class="text-lg text-base-content/70 mb-8 max-w-lg">
            Oops! Something went wrong with your request. 
            The server couldn't understand what you were asking for.
        </p>

        <!-- Suggestions -->
        <div class="bg-base-200 p-6 rounded-lg mb-8 w-full max-w-lg">
            <h2 class="font-semibold mb-4 text-lg">Here's what you can try:</h2>
            <ul class="space-y-2 text-start text-base-content/70">
                <li class="flex items-center gap-2">
                    <div class="w-2 h-2 rounded-full bg-primary"></div>
                    Check if the URL is correct
                </li>
                <li class="flex items-center gap-2">
                    <div class="w-2 h-2 rounded-full bg-primary"></div>
                    Make sure all required fields are filled
                </li>
                <li class="flex items-center gap-2">
                    <div class="w-2 h-2 rounded-full bg-primary"></div>
                    Double-check your input values
                </li>
            </ul>
        </div>

        <!-- Buttons -->
        <div class="flex gap-4">
            <button onclick="history.back()" class="btn btn-outline">
                <span class="material-symbols-outlined">arrow_back</span>
                Go Back
            </button>
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary">
                <span class="material-symbols-outlined">home</span>
                Home Page
            </a>
        </div>

        <!-- Error Code Reference -->
        <p class="mt-12 text-base-content/30 text-sm">
            Error Code: 400 Bad Request
        </p>
    </div>

    <!-- Simple gradient background -->
    <div class="fixed inset-0 -z-10 bg-base-100">
        <div class="absolute inset-0 bg-gradient-to-br from-base-200/50 via-transparent to-base-300/50"></div>
    </div>
</body>
</html>