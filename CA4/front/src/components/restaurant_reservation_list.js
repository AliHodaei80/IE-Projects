import React from "react";
import { useState, useEffect } from "react";
import { fetchData } from "../utils/request_utils.js";

function RestaurantReservationList({ restaurantId, tableSelected }) {
  const [restReservations, setRestReservations] = useState([]);
  const [datetimeFilter, setDatetimeFilter] = useState("");

  let filteredReservations = restReservations.filter((reservation) =>
    datetimeFilter
      ? new Date(reservation.datetime).toDateString() ===
        new Date(datetimeFilter).toDateString()
      : true
  );

  if (tableSelected !== -1) {
    filteredReservations = filteredReservations.filter(
      (reservation) => reservation.tableNumber === tableSelected
    );
  }

  const handleDatetimeChange = (e) => {
    console.log("time " + e.target.value);
    setDatetimeFilter(e.target.value);
  };

  const handleFetchRestaurantReservations = (response) => {
    console.log(response);
    if (response.success) {
      setRestReservations(response.data.reservations);
    } else {
      console.log("error happend");
    }
  };

  const fetchRestaurantReservations = () => {
    fetchData(
      "/restaurants/" + restaurantId + "/reservations",
      null,
      handleFetchRestaurantReservations,
      (res) => {}
    );
  };

  useEffect(() => {
    fetchRestaurantReservations();
  }, []);

  return (
    <div className="col-4 h-100" id="reservation-list">
      {restReservations.length > 0 ? (
        <div className="d-flex flex-column align-items-center p-2">
          <div className="d-flex container-fluid align-items-center p-0">
            <div id="manage-rest-reservation-list-header">Reservation List</div>
            <input
              className="form-control w-25 rounded-4 ms-auto p-1"
              type="date"
              name="date"
              id="date"
              onChange={handleDatetimeChange}
              required
            />
          </div>

          {tableSelected === -1 ? (
            <div
              className="ms-auto mt-1"
              id="manage-rest-reservation-list-info"
            >
              Select a table to see its reservations
            </div>
          ) : (
            <div className="ms-auto mt-1" id="table-reservations">
              Reservations for Table-{tableSelected}
            </div>
          )}
        </div>
      ) : (
        <div className="d-flex align-items-center p-2">
          <div id="manage-rest-reservation-list-header">Reservation List</div>
          <div className="ms-auto" id="empty-reservation">
            No Reservation
          </div>
        </div>
      )}

      <div className="table-responsive">
        <table
          id="manage-rest-reservations"
          className="table-responsive table rounded-3 overflow-hidden"
        >
          <tbody>
            {filteredReservations.map((reservation) => (
              <tr
                className={
                  reservation.canceled
                    ? "manage-reservation-done"
                    : "manage-reservation-active"
                }
              >
                <td className="manage-reservation-date">
                  {new Date(reservation.datetime).toLocaleString()}
                </td>
                <td className="reserver">{"By " + reservation.username}</td>
                <td className="manage-table-id">
                  {" Table " + reservation.tableNumber}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

export default RestaurantReservationList;
