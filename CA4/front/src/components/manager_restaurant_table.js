import React from "react";
import AddRestaurantModal from "./add_restaurant_modal.js";

import "../styles/manager_restaurants.css";
import ManagerRestaurantsTableHeader from "./manager_restaurant_table_header.js";
let restaurants = [
  {
    id: 4,
    name: "The Winery Restaurant & Wine Bar",
    avgAmbianceScore: 0.0,
    avgOverallScore: 0.0,
    avgServiceScore: 0.0,
    avgFoodScore: 0.0,
    startTime: "12:00:00",
    endTime: "21:00:00",
    type: "Seafood",
    description:
      "The Winery Restaurant & Wine Bar has been focused on successfully pairing contemporary California regional cuisine, with a hip, vibrant, sophisticated setting, to create a cutting-edge dining experience. When Partners JC Clow, William Lewis and Chef Yvon Goetz set out to deliver a culinary experience straight from wine county, little did they know that they would also earn the title of Restaurateurs of the Year for their achievements and have their restaurants in Newport Beach and Tustin earn the title of Restaurant of the Year multiple times! The three partners strive to deliver the best dining experience In Orange County on a nightly basis. Chef Goetz, whose accolades include the AAA Five Diamond Award and multiple Chef of the Year honors, describes his menu at The Winery Restaurant as wine country-driven a perfect match for a wine program that features a list with 650 selections that change weekly and climate-controlled cellars, which can house up to 7,500 bottles!",
    managerUsername: "amin",
    address: {
      city: "San Diego",
      country: "US",
      street: "4301 La Jolla Village Drive Suite 2040",
    },
  },
  {
    id: 12,
    name: "Restaurant Club Social Mexicano",
    avgAmbianceScore: 0.0,
    avgOverallScore: 0.0,
    avgServiceScore: 0.0,
    avgFoodScore: 0.0,
    startTime: "11:00:00",
    endTime: "23:00:00",
    type: "Mexican",
    description:
      "The restaurant offers authentic, fresh, homemade and handmade national dishes of modern Mexican cuisine. The menu includes fish and meat, as well as vegetarian, gluten-free and vegan dishes and sweet delicacies. The offer is based on a sharing model.",
    managerUsername: "amin",
    address: {
      city: "Frankfurt",
      country: "Germany",
      street: "Große Eschenheimer Straße",
    },
  },
  {
    id: 9,
    name: "BREW+BLOOM",
    avgAmbianceScore: 0.0,
    avgOverallScore: 0.0,
    avgServiceScore: 0.0,
    avgFoodScore: 0.0,
    startTime: "09:00:00",
    endTime: "21:00:00",
    type: "Contemporary Canadian",
    description:
      "We are a floral-inspired restaurant located in downtown Edmonton. We offer artisan coffee and a wide range of beautifully curated dishes made from locally sourced ingredients. Choose to dine within two unique dining areas: The Flower Tree and The Flower House.",
    managerUsername: "amin",
    address: {
      city: "Edmonton",
      country: "Canada",
      street: "10550 115 St NW",
    },
  },
];

function ManagerRestaurantsTable() {
  const [addRestModalShow, setAddRestModalShow] = React.useState(false);
  let restaurantList = restaurants.map((restaurant) => (
    <tr key={restaurant.id}>
      <td className="restaurant-name">{restaurant.name}</td>
      <td className="restaurant-address text-center">
        {restaurant.address.city}, {restaurant.address.country}
      </td>
      <td className="text-end">
        <button className="manager-operation manage rounded-3 border-0">
          Manage
        </button>
      </td>
    </tr>
  ));
  let content = (
    <div className="table-responsive mx-auto w-100 mt-4 table-container p-4">
      <table className="table align-middle">
        <ManagerRestaurantsTableHeader
          onClick={() => setAddRestModalShow(true)}
        />
        <tbody>{restaurantList}</tbody>
      </table>
      <AddRestaurantModal
        show={addRestModalShow}
        onHide={() => setAddRestModalShow(false)}
      />
    </div>
  );
  return content;
}

export default ManagerRestaurantsTable;
