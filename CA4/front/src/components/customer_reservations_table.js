import React from "react";

let username = "Ali_Hodaei";
let reservations = [
  {
    username: "Ali_Hodaei",
    restaurantName: "The Commoner",
    tableNumber: 3,
    datetime: "2024-05-05T20:00:00",
    reservationId: 2,
    restaurantId: 1,
    canceled: false,
    seatsReserved: 7,
  },
  {
    username: "Ali_Hodaei",
    restaurantName: "The Commoner",
    tableNumber: 3,
    datetime: "2024-05-04T21:00:00",
    reservationId: 1,
    restaurantId: 1,
    canceled: false,
    seatsReserved: 7,
  },
  {
    username: "Ali_Hodaei",
    restaurantName: "The Commoner",
    tableNumber: 3,
    datetime: "2024-05-06T20:00:00",
    reservationId: 3,
    restaurantId: 1,
    canceled: false,
    seatsReserved: 7,
  },
  {
    username: "Ali_Hodaei",
    restaurantName: "The Commoner",
    tableNumber: 4,
    datetime: "2024-05-06T20:00:00",
    reservationId: 4,
    restaurantId: 1,
    canceled: true,
    seatsReserved: 10,
  },
];
function CustomerReservations() {
  return (
    <div className="table-responsive">
      <table className="table-responsive table mt-4 rounded-3 overflow-hidden">
        <thead className="reservations-header">
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
                <a href="#">{reservation.restaurantName}</a>
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
                  <a href="#">Cancel</a>
                ) : (
                  <a href="#">Add Comment</a>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default CustomerReservations;
