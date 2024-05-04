import React from "react";

// import "../styles/footer.css";
let restReservations = [
  {
    username: "Mostafa_Ebrahimi",
    restaurantName: "The Commoner",
    tableNumber: 1,
    datetime: "2024-12-03T15:00:00",
    canceled: true,
    reservationId: 1,
  },
  {
    username: "Mostafa_Ebrahimi",
    restaurantName: "The Commoner",
    tableNumber: 2,
    datetime: "2024-12-03T15:00:00",
    reservationId: 2,
    canceled: false,
  },
  {
    username: "Mostafa_Ebrahimi",
    restaurantName: "The Commoner",
    tableNumber: 2,
    datetime: "2025-12-03T15:00:00",
    reservationId: 3,
    canceled: true,
  },
];

function RestaurantReservationList() {
  return (
    <div className="col-4 h-100" id="reservation-list">
      <div className="d-flex align-items-center p-2">
        <div id="manage-rest-reservation-list-header">Reservation List</div>
        {restReservations.length > 0 ? (
          <div className="ms-auto" id="manage-rest-reservation-list-info">
            Select a table to see its reservations
          </div>
        ) : (
          <div className="ms-auto" id="empty-reservation">
            No Reservation
          </div>
        )}
      </div>

      <div className="table-responsive">
        <table className="table-responsive table rounded-3 overflow-hidden">
          <tbody>
            {restReservations.map((reservation) => (
              <tr
                className={
                  reservation.canceled
                    ? "manage-reservation-done"
                    : "manage-reservation-active"
                }
              >
                <td className="manage-reservation-date">
                  {reservation.datetime}
                </td>
                <td className="reserver">{"By " + reservation.username}</td>
                <td className="manage-table-id">
                  <a href="#">{" Table " + reservation.tableNumber}</a>
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
