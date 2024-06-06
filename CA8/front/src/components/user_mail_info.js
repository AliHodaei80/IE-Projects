import React from "react";

import "../styles/user_info_bar.css";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import { postData, sendToast } from "../utils/request_utils";
import { useState, useEffect } from "react";
import Cookies from "js-cookie";
import { fetchData } from "../utils/request_utils.js";

const user_details_endpoint = "/user/";

function UserMailInfo() {
  const { authDetails, setAuthDetails } = useAuth();
  const navigate = useNavigate();
  const [userDetails, setUserDetails] = useState({
    address: { city: "", country: "" },
  });

  const handleFetchUserDetails = (response) => {
    console.log(response);
    if (response.success) {
      setUserDetails(response.data.user);
    } else {
      console.log("error happend");
    }
  };
  const fetchUserDeatails = () => {
    fetchData(
      user_details_endpoint + authDetails.user.username,
      {},
      handleFetchUserDetails,
      (res) => {},
      true,
      authDetails
    );
  };

  useEffect(() => {
    fetchUserDeatails();
  }, []);

  const handleLogout = () => {
    Cookies.remove("authDetails");
    setAuthDetails({ logged_in: false });
    navigate("/authenticate");
    sendToast(true, "Logout Succesful");
  };

  return (
    <div className="user-info align-items-center d-flex p-2 rounded-3 mt-2">
      <div className="mail-info">
        {"MANAGER" === authDetails.role
          ? "Each Restaurants new reservations are emailed to "
          : "Your reservations are also emailed to "}
        <a className="mail-address" href={"mailto:" + userDetails.email + ""}>
          {userDetails.email}
        </a>
      </div>
      {"MANAGER" !== authDetails.role ? (
        <div className="address ms-auto">
          Address: {userDetails.address.city}, {userDetails.address.country}
        </div>
      ) : (
        <div className="address ms-auto"></div>
      )}

      <button
        id="logout-button"
        className="rounded-3 border-0"
        onClick={handleLogout}
      >
        Logout
      </button>
    </div>
  );
}

export default UserMailInfo;
