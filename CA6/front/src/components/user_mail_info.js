import React from "react";

import "../styles/user_info_bar.css";
import { useAuth } from "../context/AuthContext";
import { useNavigate } from "react-router-dom";
import { postData, sendToast } from "../utils/request_utils";
import Cookies from "js-cookie";
function UserMailInfo() {
  const { authDetails, setAuthDetails } = useAuth();
  const navigate = useNavigate();
  const handleLogout = () => {
    Cookies.remove("authDetails");
    setAuthDetails({ logged_in: false });
    navigate("/authenticate");
    sendToast(true, "Logout Succesful");
    // postData(
    //   "/logout",
    //   {},
    //   () => {},
    //   () => {
    //     navigate("/authenticate");
    //     sendToast(true, "Logged out successfully");
    //     const authDetailsCopy=authDetails;
    //     authDetailsCopy.logged_in=false;
    //     setAuthDetails(authDetailsCopy);
    //   },
    //   () => {
    //     sendToast(false, "Logout failed");
    //   }
    // );
  };
  let content = (
    <div className="user-info align-items-center d-flex p-2 rounded-3 mt-2">
      <div className="mail-info">
        {"MANAGER" === authDetails.role
          ? "Each Restaurants new reservations are emailed to "
          : "Your reservations are also emailed to "}
        <a className="mail-address" href={"mailto:" + authDetails.email + ""}>
          {authDetails.email}
        </a>
      </div>
      {"MANAGER" !== authDetails.role ? (
        <div className="address ms-auto">
          Address: {authDetails.user.address.city},{" "}
          {authDetails.user.address.country}
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
  return content;
}

export default UserMailInfo;
