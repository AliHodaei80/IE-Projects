import React, { useEffect, useState } from "react";

export default function OpeningList({ openingData, targetTimeSetter }) {
  const [localTargetDate, setLocalTargetDate] = useState();
  const formatTime = (datetime) => {
    const date = new Date(datetime);
    return date.toLocaleTimeString("en-US", {
      hour: "2-digit",
      minute: "2-digit",
      hour12: true,
    });
  };
  const chunkArray = (array, size) => {
    const chunked = [];
    for (let i = 0; i < array.length; i += size) {
      chunked.push(array.slice(i, i + size));
    }
    return chunked;
  };

  useEffect(() => {
    console.log(localTargetDate);
    targetTimeSetter(localTargetDate);
  }, [localTargetDate]);

  const timeButtons = openingData.sort();
  const rows = chunkArray(timeButtons, 6);
  return (
    <div className="text-center mt-3 ms-0">
      {rows.map((row, index) => (
        <div className="row" key={index}>
          {row.map((time, index) => (
            <button
              className="reserve-blob col-sm ms-2 rounded-4 mt-2"
              key={index}
              onClick={() => {
                targetTimeSetter(time);
              }}
            >
              {formatTime(time)}
            </button>
          ))}
        </div>
      ))}
    </div>
  );
}
