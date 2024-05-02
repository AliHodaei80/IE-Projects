import React from "react";
import "../styles/manager_restaurants.css";

function ManagerRestaurantsTableHeader(props) {
  let content = (
    <thead>
      <tr>
        <th colSpan="2" scope="colgroup">
          My Restaurants
        </th>
        <td className="text-end">
          <button
            data-bs-toggle="modal"
            data-bs-target="#addRestModal"
            className="manager-operation add rounded-3 border-0"
          >
            Add
          </button>
        </td>
      </tr>
    </thead>
  );
  return content;
}

export default ManagerRestaurantsTableHeader;
