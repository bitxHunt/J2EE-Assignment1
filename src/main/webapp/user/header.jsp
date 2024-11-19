<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Header</title>
    <link
      href="https://cdn.jsdelivr.net/npm/daisyui@4.12.14/dist/full.min.css"
      rel="stylesheet"
      type="text/css"
    />
    <script src="https://cdn.tailwindcss.com"></script>
  </head>
  <body>
    <div class="navbar bg-base-100">
      <div class="navbar-start">
        <div class="dropdown">
          <div tabindex="0" role="button" class="btn btn-ghost lg:hidden">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              class="h-5 w-5"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M4 6h16M4 12h8m-8 6h16"
              />
            </svg>
          </div>
          <ul
            tabindex="0"
            class="menu menu-sm dropdown-content bg-base-100 rounded-box z-[1] mt-3 w-52 p-2 shadow"
          >
            <li><a>About</a></li>
            <li>
              <a>Services</a>
              <ul class="p-2">
                <li><a>Service 1</a></li>
                <li><a>Service 1</a></li>
              </ul>
            </li>
            <li><a>Booking</a></li>
          </ul>
        </div>
        <a class="btn btn-ghost text-xl">CleanX</a>
      </div>
      <div class="navbar-center hidden lg:flex">
        <ul class="menu menu-horizontal px-1">
          <li><a>About</a></li>
          <li>
            <details>
              <summary>Services</summary>
              <ul class="p-2">
                <li><a>Service 1</a></li>
                <li><a>Service 2</a></li>
              </ul>
            </details>
          </li>
          <li><a>Booking</a></li>
        </ul>
      </div>
      <div class="navbar-end">
        <a class="btn">Login</a>
      </div>
    </div>
  </body>
</html>
