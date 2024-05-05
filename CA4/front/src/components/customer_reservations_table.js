import React from "react";
import { useState, useEffect } from "react";
import CancelConfirmationModal from "./cancel_confimation_modal";
import { fetchData } from "../utils/request_utils.js";
import { Link } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

function CustomerReservations() {
  const { authDetails } = useAuth();
  const [cancelReservationIds, setCancelReservationId] = useState(-1);
  const [reservations, setReservations] = useState([]);

  const handleFetchReservations = (response) => {
    console.log(response);
    if (response.success) {
      setReservations(response.data.reservations);
    } else {
      console.log("error happend");
    }
  };

  const fetchReservations = () => {
    fetchData(
      "/reservations/" + authDetails.username,
      null,
      handleFetchReservations,
      (res) => {}
    );
  };

  useEffect(() => {
    fetchReservations();
  }, []);

  return (
    <div class="table-responsive">
      <table class="table-responsive table mt-4 rounded-3 overflow-hidden">
        <thead class="reservations-header">
          <tr>
            <th colspan="5" scope="colgroup">
              My Reservations
            </th>
          </tr>
        </thead>
        <tbody>
          {reservations.map((reservation) => (
            <tr
              className={
                reservation.canceled
                  ? "reservation-cancelled"
                  : new Date() < new Date(reservation.datetime)
                  ? "reservation-active"
                  : "reservation-done"
              }
            >
              <td className="reservation-date">
                {new Date(reservation.datetime).toLocaleString("en-US", {
                  year: "numeric",
                  month: "2-digit",
                  day: "2-digit",
                  hour: "2-digit",
                  minute: "2-digit",
                })}
              </td>
              <td className="restaurant-link">
                <Link
                  to={{
                    pathname: "/restaurant/" + reservation.restaurantId,
                    state: {},
                  }}
                >
                  {reservation.restaurantName}
                </Link>
              </td>
              <td className="table-id">Table-{reservation.tableNumber}</td>
              <td className="seats-num">{reservation.seatsReserved} Seats</td>
              <td
                className={
                  reservation.canceled
                    ? "text-end no-operation"
                    : new Date() < new Date(reservation.datetime)
                    ? "operation cancel text-end"
                    : "operation add-comment text-end"
                }
              >
                {reservation.canceled ? (
                  <div>Canceled</div>
                ) : new Date() < new Date(reservation.datetime) ? (
                  <Link
                    href="#"
                    data-bs-toggle="modal"
                    data-bs-target="#cancelConfirmationModal"
                    onClick={() => {
                      setCancelReservationId(reservation.reservationId);
                    }}
                  >
                    Cancel
                  </Link>
                ) : (
                  <Link href="#">Add Comment</Link>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      <CancelConfirmationModal
        reservationId={cancelReservationIds}
        unSetCancelReservationId={() => {
          setCancelReservationId(-1);
          fetchReservations();
          console.log("cancelreservation id " + cancelReservationIds);
        }}
        restaurantName={
          reservations.find(
            (reservation) => reservation.reservationId === cancelReservationIds
          ) != null
            ? reservations.find(
                (reservation) =>
                  reservation.reservationId === cancelReservationIds
              ).restaurantName
            : null
        }
      />
    </div>
  );
}

export default CustomerReservations;
