import React, { useState } from "react";
import axios from "axios";
import Header from "./header.js";
import Footer from "./footer.js";
import logo from "../images/logo.png";

const Signup = () => {
  const [userData, setUserData] = useState({
    role: "client",
    username: "",
    password: "",
    email: "",
    address: {
      country: "",
      city: "",
    },
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUserData((prevUserData) => ({
      ...prevUserData,
      [name]: value,
    }));
  };

  const sendSignup = async () => {
    console.log("Sending signup request", userData);
    try {
      const response = await axios.post(
        "http://127.0.0.1:8080/signup",
        userData
      );
      console.log(response.data);
    } catch (error) {
      console.error("Error sending signup request:", error);
    }
  };

  return (
    <main>
      <header>
        <Header />
      </header>
      <div className="d-flex flex-column flex-grow-1">
        <div className="wrapper">
          <div className="logo">
            <img src={logo} alt="" />
          </div>
          <div className="text-center mt-4 name">SIGNUP</div>
          <form className="p-3 mt-3">
            <div className="form-field d-flex align-items-center">
              <input
                type="text"
                name="username"
                id="userName"
                placeholder="Username"
                value={userData.username}
                onChange={handleChange}
              />
            </div>
            <div className="form-field d-flex align-items-center">
              <input
                type="email"
                name="email"
                id="email"
                placeholder="Email"
                value={userData.email}
                onChange={handleChange}
              />
            </div>
            <div class="d-flex user-type align-items-center">
              <p class="mb-0 me-4 gender-title">Role:</p>

              <div class="form-check form-check-inline mb-0 me-4">
                <input
                  class="form-check-input"
                  type="radio"
                  name="role"
                  id="customer-type"
                  value="customer"
                  onChange={handleChange}
                />
                <label class="form-check-label" for="customer-type">
                  Customer
                </label>
              </div>

              <div class="form-check form-check-inline mb-0 me-4">
                <input
                  class="form-check-input"
                  type="radio"
                  name="role"
                  id="manager-type"
                  value="manager"
                  onChange={handleChange}
                />
                <label class="form-check-label" for="manager-type">
                  Manager
                </label>
              </div>
            </div>
            <div className="form-field d-flex align-items-center">
              <input
                type="text"
                name="address.country"
                id="country"
                placeholder="Country"
                value={userData.address.country}
                onChange={handleChange}
              />
            </div>
            <div className="form-field d-flex align-items-center">
              <input
                type="text"
                name="address.city"
                id="city"
                placeholder="City"
                value={userData.address.city}
                onChange={handleChange}
              />
            </div>
            <div className="form-field d-flex align-items-center">
              <input
                type="password"
                name="password"
                id="password"
                placeholder="Password"
                value={userData.password}
                onChange={handleChange}
              />
            </div>
            <button type="button" onClick={sendSignup} className="btn mt-3">
              Signup
            </button>
          </form>
          <div className="text-center fs-6">
            Have already an account? <a href="#">Login</a>
          </div>
        </div>
      </div>
      <Footer />
    </main>
  );
};

export default Signup;
