import React from 'react';

const TimeInfoComponent = ({ startTime, endTime }) => {
    const getCurrentTimeInfo = () => {
        const now = new Date();
        const currentHours = now.getHours();
        const currentMinutes = now.getMinutes();
        const [startHour, startMinute] = startTime.split(":").map(Number);
        const [endHour, endMinute] = endTime.split(":").map(Number);

        const isOpen = (currentHours > startHour || (currentHours === startHour && currentMinutes >= startMinute)) &&
                       (currentHours < endHour || (currentHours === endHour && currentMinutes < endMinute));

        const formatTime = (hour, minute) => {
            const isPM = hour >= 12;
            const displayHour = hour % 12 || 12; // convert to 12-hour format
            return `${displayHour}:${minute.toString().padStart(2, '0')} ${isPM ? 'PM' : 'AM'}`;
        };

        if (isOpen) {
            return `Closes at ${formatTime(endHour, endMinute)}`;
        } else {
            return `Opens at ${formatTime(startHour, startMinute)}`;
        }
    };

    return (
        <div className="close-time">
            {getCurrentTimeInfo()}
        </div>
    );
};

export default TimeInfoComponent;
