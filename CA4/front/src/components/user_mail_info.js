import React from "react";

import "../styles/user_info_bar.css";
let user = {
  username: "ali",
  password: "ali_1234",
  email: "ali@gmail.com",
  address: {
    city: "Pittsburgh",
    country: "US",
  },
  role: "MANAGER",
};

function UserMailInfo() {
  let content = (
    <div className="user-info align-items-center d-flex p-2 rounded-3 mt-2">
      <div className="mail-info">
        {"MANAGER" === user.role
          ? "Each Restaurants new reservations are emailed to "
          : "Your reservations are also emailed to "}
        <a className="mail-address" href={"mailto:" + user.email + ""}>
          {user.email}
        </a>
      </div>
      {"MANAGER" !== user.role ? (
        <div className="address ms-auto">
          Address: {user.address.city}, {user.address.country}
        </div>
      ) : (
        <div className="address ms-auto"></div>
      )}

      <button id="logout-button" className="rounded-3 border-0">
        Logout
      </button>
    </div>
  );
  return content;
}

export default UserMailInfo;
