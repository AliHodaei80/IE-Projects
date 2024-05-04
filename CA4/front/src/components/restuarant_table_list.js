import React from "react";
import hashtag from "../images/icons/hashtag.svg";
import seat from "../images/icons/seat.svg";
import { Link } from "react-router-dom";
import AddTableModal from "./add_table_modal.js";
import { useState, useEffect } from "react";
import { fetchData } from "../utils/request_utils.js";

import "bootstrap/dist/js/bootstrap.js";
// import "../styles/footer.css";

function RestaurantTableList({ restaurantId }) {
  const [restaurantTables, setRestaurantTables] = useState([]);

  const handleFetchRestaurantTables = (response) => {
    console.log(response);
    if (response.success) {
      setRestaurantTables(response.data.restaurantTables);
    } else {
      console.log("error happend");
    }
  };

  const fetchRestaurantTables = () => {
    fetchData(
      "/tables/" + restaurantId,
      null,
      handleFetchRestaurantTables,
      (res) => {}
    );
  };

  useEffect(() => {
    fetchRestaurantTables();
  }, []);

  return (
    <>
      <div className="col-8 h-100" id="add-table">
        <div className="ms-0 mt-1" id="add-table-link">
          <Link data-bs-toggle="modal" data-bs-target="#addTableModal">
            + Add Table
          </Link>
        </div>
        {restaurantTables.length > 0 ? (
          <div className="container p-4">
            <div className="row mt-4 w-75 mx-auto row-cols-2 row-cols-sm-3 row-cols-md-4 row-cols-xl-5 row-cols-xxl-6 g-4">
              {restaurantTables.map((restTable) => (
                <div className="col">
                  <div className="rest-table mx-auto d-flex flex-column justify-content-center rounded-4">
                    <div className="icon-num d-flex justify-content-evenly">
                      <img
                        className="table-manage-icon align-self-center"
                        // src="../images/icons/hashtag.svg"
                        src={hashtag}
                        alt="hashtag-icon"
                      />
                      <div>{restTable.tableNumber}</div>
                    </div>
                    <div className="icon-num d-flex justify-content-evenly">
                      <img
                        className="table-manage-icon align-self-center"
                        src={seat}
                        alt="seat-icon"
                      />
                      <div>{restTable.seatsNumber}</div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </div>
        ) : (
          <div className="d-flex h-75 justify-content-center align-items-center ">
            No Tables have been added.
          </div>
        )}
      </div>
      <AddTableModal
        restaurantId={restaurantId}
        fetchTables={fetchRestaurantTables}
      />
    </>
  );
}

export default RestaurantTableList;
