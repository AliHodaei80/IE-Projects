import React from "react";
import hashtag from "../images/icons/hashtag.svg";
import seat from "../images/icons/seat.svg";
import { Link } from "react-router-dom";
import AddTableModal from "./add_table_modal.js";
import { useState, useEffect } from "react";
import { fetchData } from "../utils/request_utils.js";

import "bootstrap/dist/js/bootstrap.js";

function RestaurantTableList({
  restaurantId,
  tableSelected,
  setTableSelected,
}) {
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

  const handleTableClicked = (tableNumber) => {
    console.log("table number " + tableNumber);
    if (tableSelected === tableNumber) {
      setTableSelected(-1);
      console.log("table selected " + tableSelected);
    } else {
      setTableSelected(tableNumber);
      console.log("table selected " + tableSelected);
    }
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
                <div
                  key={restTable.tableNumber}
                  className="col"
                  //   onClick={(e) => {
                  //     console.log(e);
                  //   }}
                >
                  <div className="rest-table mx-auto d-flex flex-column justify-content-center rounded-4">
                    <button
                      className={
                        tableSelected === restTable.tableNumber
                          ? "table-button rounded-4 table-selected"
                          : "table-button rounded-4"
                      }
                      onClick={() => {
                        console.log(restTable.tableNumber);
                        handleTableClicked(restTable.tableNumber);
                      }}
                    >
                      <div className="icon-num d-flex justify-content-evenly">
                        <img
                          className="table-manage-icon align-self-center"
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
                    </button>
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
