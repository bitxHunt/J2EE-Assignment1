<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>Login Form</title>
    <link
      href="https://cdn.jsdelivr.net/npm/daisyui@4.12.14/dist/full.min.css"
      rel="stylesheet"
      type="text/css"
    />
    <script src="https://cdn.tailwindcss.com"></script>
    <link
      rel="stylesheet"
      href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0&icon_names=vacuum"
    />
  </head>
  <!-- min-h-screen ensures full height, flex and items-center vertically centers content -->
  <body class="min-h-screen flex items-center justify-center">
    <!-- Container with padding for spacing -->
    <div class="container mx-auto px-4 lg:px-0">
      <!-- Card wrapper with max-width and centered -->
      <div class="max-w-4xl mx-auto">
        <!-- Flex container -->
        <div class="flex flex-col items-center gap-8">
          <!-- Form container - full width on mobile, half on desktop -->

          <!-- Top Brand Name and Welcome Message-->
          <div class="w-full lg:w-1/2 p-8">
            <h2 class="text-3xl font-bold mb-6 text-center">Welcome Back</h2>
            <div class="text-center lg:pb-4">
              <span class="material-symbols-outlined text-blue-500 text-5xl">
                vacuum
              </span>
              <p class="lg:text-xl md:text-2xl md:m-3">
                Clean <span class="italic text-sky-400 font-bold">X</span>
              </p>
            </div>

            <!-- Form -->
            <form class="space-y-6">
              <!-- Email -->
              <div class="form-control">
                <label class="input input-bordered flex items-center gap-2">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 16 16"
                    fill="currentColor"
                    class="h-4 w-4 opacity-70"
                  >
                    <path
                      d="M2.5 3A1.5 1.5 0 0 0 1 4.5v.793c.026.009.051.02.076.032L7.674 8.51c.206.1.446.1.652 0l6.598-3.185A.755.755 0 0 1 15 5.293V4.5A1.5 1.5 0 0 0 13.5 3h-11Z"
                    />
                    <path
                      d="M15 6.954 8.978 9.86a2.25 2.25 0 0 1-1.956 0L1 6.954V11.5A1.5 1.5 0 0 0 2.5 13h11a1.5 1.5 0 0 0 1.5-1.5V6.954Z"
                    />
                  </svg>
                  <input
                    type="text"
                    class="grow"
                    placeholder="Email"
                    name="email"
                  />
                </label>
              </div>

              <!-- Password -->
              <div class="form-control">
                <label class="input input-bordered flex items-center gap-2">
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 16 16"
                    fill="currentColor"
                    class="h-4 w-4 opacity-70"
                  >
                    <path
                      fill-rule="evenodd"
                      d="M14 6a4 4 0 0 1-4.899 3.899l-1.955 1.955a.5.5 0 0 1-.353.146H5v1.5a.5.5 0 0 1-.5.5h-2a.5.5 0 0 1-.5-.5v-2.293a.5.5 0 0 1 .146-.353l3.955-3.955A4 4 0 1 1 14 6Zm-4-2a.75.75 0 0 0 0 1.5.5.5 0 0 1 .5.5.75.75 0 0 0 1.5 0 2 2 0 0 0-2-2Z"
                      clip-rule="evenodd"
                    />
                  </svg>
                  <input
                    type="password"
                    class="grow"
                    placeholder="Password"
                    name="password"
                  />
                </label>
              </div>

              <!-- Forgot Password link -->
              <div class="row mt-4">
                <a
                  href=""
                  class="text-blue-500 hover:text-blue-700 font-semibold transition duration-200 p-1"
                >
                  Forgot Password?
                </a>
              </div>

              <!-- Submit Button -->
              <button class="btn btn-primary w-full">Login</button>

              <!-- Register link -->
              <div class="row mt-4 text-center">
                <span>Don't have an account?</span>
                <a
                  href="${pageContext.request.contextPath}/register"
                  class="text-blue-500 hover:text-blue-700 font-semibold transition duration-200 p-1"
                >
                  Register
                </a>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
