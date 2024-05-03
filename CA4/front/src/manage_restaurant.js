import React from "react";
import { useParams } from "react-router-dom";

function ManageRestaurantPage() {
  const { id } = useParams();

  // Fetch restaurant details based on id

  return (
    <div>
      <h1>Restaurant Details</h1>
      <p>Restaurant ID: {id}</p>
      {/* Display more details here */}
    </div>
  );
}

export default ManageRestaurantPage;
