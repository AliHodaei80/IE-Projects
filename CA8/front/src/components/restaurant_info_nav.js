import React from "react";

// import "../styles/footer.css";

function RestaurantInfoNavBar({ restName, address }) {
  return (
    <div className="container-fluid py-3">
      <div className="d-flex align-items-center">
        <div id="manage-rest-name">{restName}</div>
        <div className="ms-auto" id="manage-rest-address">
          {"Address: " +
            address.street +
            ", " +
            address.city +
            ", " +
            address.country}
        </div>
      </div>
    </div>
  );
}

export default RestaurantInfoNavBar;
