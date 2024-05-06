import React from "react";

const formatTime = (hour, minute) => {
  const isPM = hour >= 12;
  const displayHour = hour % 12 || 12; // convert to 12-hour format
  return `${displayHour}:${minute.toString().padStart(2, "0")} ${
    isPM ? "PM" : "AM"
  }`;
};

export const isOpen = (startTime, endTime) => {
  const now = new Date(startTime, endTime);
  const currentHours = now.getHours();
  const currentMinutes = now.getMinutes();
  const [startHour, startMinute] = startTime.split(":").map(Number);
  const [endHour, endMinute] = endTime.split(":").map(Number);

  const isOpen =
    (currentHours > startHour ||
      (currentHours === startHour && currentMinutes >= startMinute)) &&
    (currentHours < endHour ||
      (currentHours === endHour && currentMinutes < endMinute));
};
const getCurrentTimeInfo = (startTime, endTime) => {
  const now = new Date(startTime, endTime);
  const currentHours = now.getHours();
  const currentMinutes = now.getMinutes();
  const [startHour, startMinute] = startTime.split(":").map(Number);
  const [endHour, endMinute] = endTime.split(":").map(Number);

  if (isOpen(startTime, endTime)) {
    return `Closes at ${formatTime(endHour, endMinute)}`;
  } else {
    return `Opens at ${formatTime(startHour, startMinute)}`;
  }
};

const TimeInfoComponent = ({ startTime, endTime }) => {
  return (
    <div className="close-time">{getCurrentTimeInfo(startTime, endTime)}</div>
  );
};

export default TimeInfoComponent;
