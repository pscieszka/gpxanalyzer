var map;
var marker;
var customIcon = L.divIcon({
    className: 'custom-marker',
    html: '<div style="width: 12px; height: 12px; background-color: blue; border: 2px solid white; border-radius: 50%;"></div>',
    iconSize: [12, 12],
    iconAnchor: [6, 6]
});

if (coordinates && coordinates.length > 0) {
    map = L.map('map').setView([coordinates[0][0], coordinates[0][1]], 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    var latlngs = coordinates.map(function(coord) {
        return [coord[0], coord[1]];
    });

    L.polyline(latlngs, { color: 'red' }).addTo(map);
    map.fitBounds(latlngs);
    marker = L.marker([coordinates[0][0], coordinates[0][1]], { icon: customIcon }).addTo(map);
}

var totalDistance = ("[[${totalDistance}]]").replace(',', '.');
totalDistance = parseFloat(totalDistance);
var totalDistanceFloat = parseFloat(totalDistance);

var fullKilometers = Math.floor(totalDistanceFloat);
var remainingDistance = totalDistanceFloat - fullKilometers;


function convertSecondsToTimeFormat(seconds) {
    var minutes = Math.floor(seconds / 60);
    var remainingSeconds = seconds % 60;
    return minutes + ':' + (remainingSeconds < 10 ? '0' + remainingSeconds : remainingSeconds);
}

var tableBody = document.querySelector('#paceTable tbody');
tableBody.innerHTML = '';

pacePerKm.forEach(function(pace, index) {
    var row = document.createElement('tr');

    var kmCell = document.createElement('td');
    var paceCell = document.createElement('td');
    var gapPaceCell = document.createElement('td');
    var elevationCell = document.createElement('td');
    var hrCell = document.createElement('td');

    kmCell.textContent = index + 1;
    paceCell.textContent = convertSecondsToTimeFormat(pace);
    gapPaceCell.textContent = convertSecondsToTimeFormat(gapPacePerKm[index]);
    elevationCell.textContent = averageElevationGainPerKm[index];
    hrCell.textContent = hrPerKm[index];

    row.appendChild(kmCell);
    row.appendChild(paceCell);
    row.appendChild(gapPaceCell);
    row.appendChild(elevationCell);
    row.appendChild(hrCell);

    tableBody.appendChild(row);
});

if (remainingDistance > 0) {
    var lastIndex = fullKilometers - 1;

    var row = document.createElement('tr');

    var kmCell = document.createElement('td');
    var paceCell = document.createElement('td');
    var gapPaceCell = document.createElement('td');
    var elevationCell = document.createElement('td');
    var hrCell = document.createElement('td');

    kmCell.textContent = remainingDistance.toFixed(2);
    paceCell.textContent = convertSecondsToTimeFormat(pacePerKm[lastIndex]);
    gapPaceCell.textContent = convertSecondsToTimeFormat(gapPacePerKm[lastIndex]);
    elevationCell.textContent = averageElevationGainPerKm[lastIndex];
    hrCell.textContent = hrPerKm[lastIndex];

    row.appendChild(kmCell);
    row.appendChild(paceCell);
    row.appendChild(gapPaceCell);
    row.appendChild(elevationCell);
    row.appendChild(hrCell);

    tableBody.appendChild(row);
}

function updateMarkerPosition(percentage) {
    if (!coordinates || coordinates.length === 0) return;

    percentage = Math.max(0, Math.min(percentage, 100));

    var index = Math.floor((percentage / 100) * (coordinates.length - 1));
    var newPos = coordinates[index];

    if (newPos) {
        marker.setLatLng(newPos);
    }
}

var ctxPace = document.getElementById('paceChart').getContext('2d');
var maxPace = Math.max(...paceData);

var elevationVisible = false;
var hrVisible = false;
var paceVisible = true;
var gradeVisible = false;
var gapPaceVisible = false;
var yAxisDisplay = true;

function formatTime(value) {
    var minutes = Math.floor(value);
    var seconds = Math.round((value - minutes) * 60);
    return minutes + ':' + (seconds < 10 ? '0' : '') + seconds;
}

var paceChart = new Chart(ctxPace, {
    type: 'line',
    data: {
        labels: Array.from({length: paceData.length}, (_, i) => (i + 1)),
        datasets: [
            {
                label: 'Pace (min/km)',
                data: paceData,
                borderColor: 'blue',
                borderWidth: 1,
                pointRadius: 0,
                tension: 0.4,
                yAxisID: 'y',
                hidden: !paceVisible
            },
            {
                label: 'GAP (min/km)',
                data: gapPace,
                reverse: true,
                borderColor: 'green',
                borderWidth: 1,
                pointRadius: 0,
                tension: 0.4,
                yAxisID: 'y',
                hidden: true
            },
            {
                label: 'Heart rate (bpm)',
                data: HrData,
                borderColor: 'red',
                borderWidth: 1,
                pointRadius: 0,
                tension: 0.4,
                yAxisID: 'hrAxis',
                hidden: !hrVisible
            },
            {
                label: 'Grade (%)',
                data: gradeChart,
                borderColor: 'black',
                borderWidth: 1,
                pointRadius: 0,
                tension: 0.4,
                yAxisID: 'puste',
                hidden: !gradeVisible
            }
        ]
    },
    options: {
        responsive: true,
        scales: {
            x: {
                title: {
                    display: true,
                    text: 'Distance (km)'
                },
                ticks: {
                    callback: function(value, index, values) {
                        if (index % 41 === 0) {
                            return value / 41 + ' km';
                        }
                    }
                }
            },
            y: {
                title: {
                    display: true,
                    text: 'Pace (min/km)'
                },
                ticks: {
                    callback: function(value) {
                        var minutes = Math.floor(value);
                        var seconds = Math.round((value - minutes) * 60);
                        return minutes + ':' + (seconds < 10 ? '0' : '') + seconds + '/km';
                    }
                },
                reverse: true,
                position: 'left',
                display:yAxisDisplay
            },
            elevationAxis: {
                position: 'right',
                title: {
                    display: true,
                    text: 'Elevation (m)'
                },
                type: 'logarithmic',
                min: Math.min(...elevationData) - 5,
                ticks: {
                    stepSize: 100
                },
                grid: {
                    drawOnChartArea: false
                }
            },
            hrAxis: {
                display: false
            }
        },
        interaction: {
            mode: 'index',
            intersect: false,
        },
        plugins: {
            tooltip: {
                callbacks: {
                    title: function(tooltipItems) {
                        var index = tooltipItems[0].dataIndex;
                        var distance = index * 24.42;
                        return (distance / 1000).toFixed(2) + ' km';

                    },
                    label: function(tooltipItem) {
                        var datasetIndex = tooltipItem.datasetIndex;
                        var label = tooltipItem.dataset.label;

                        if (label === 'Pace (min/km)' || label === 'GAP (min/km)') {
                            var paceValue = tooltipItem.raw;
                            return label + ': ' + formatTime(paceValue);
                        } else {
                            return label + ': ' + tooltipItem.raw;
                        }
                    }
                },
                enabled: true
            },
            legend: {
                display: false
            }
        }
    }
});
paceChart.data.datasets.push({
    label: 'Wysokość (m)',
    data: elevationData,
    backgroundColor: 'rgba(200, 200, 200, 0.5)',
    borderWidth: 0,
    type: 'line',
    pointRadius: 0,
    fill: true,
    yAxisID: 'elevationAxis',
    hidden: !elevationVisible
});
paceChart.options.plugins.tooltip.external = function(context) {
    var tooltip = context.tooltip;
    if (tooltip && tooltip.dataPoints) {
        var index = tooltip.dataPoints[0].dataIndex;
        var percentage = (index / (paceData.length - 1)) * 100;
        updateMarkerPosition(percentage);
    }
};
document.getElementById('toggleGapPace').addEventListener('change', function() {
    gapPaceVisible = this.checked;
    paceChart.data.datasets[1].hidden = !gapPaceVisible;
    paceChart.options.scales.y.display = !(!paceVisible && !gapPaceVisible);
    paceChart.update();
});

document.getElementById('toggleElevation').addEventListener('change', function() {
    elevationVisible = this.checked;
    paceChart.data.datasets[4].hidden = !elevationVisible;
    paceChart.options.scales.elevationAxis.display = elevationVisible;
    paceChart.update();
});

document.getElementById('toggleHR').checked = false;
document.getElementById('toggleHR').addEventListener('change', function() {
    hrVisible = this.checked;
    paceChart.data.datasets[2].hidden = !hrVisible;
    paceChart.update();
});
document.getElementById('togglePace').addEventListener('change', function() {
    paceVisible = this.checked;
    paceChart.data.datasets[0].hidden = !paceVisible;
    paceChart.options.scales.y.display = !(!paceVisible && !gapPaceVisible);
    paceChart.update();
});
document.getElementById('toggleGrade').addEventListener('change', function() {
    gradeChart = this.checked;
    paceChart.data.datasets[3].hidden = !gradeChart;
    paceChart.update();
});

var ctxHrPaceRatio = document.getElementById('hrPaceRatioChart').getContext('2d');

var hrPaceRatioChart = new Chart(ctxHrPaceRatio, {
    type: 'line',
    data: {
        labels: Array.from({ length: hrPaceRatios.length }, (_, i) => (i + 1)),
        datasets: [
            {
                label: 'HR/Pace Ratio',
                data: hrPaceRatios,
                borderColor: 'purple',
                borderWidth: 2,
                pointRadius: 0,
                fill: false,
                tension: 0.4,

            },
            {
                label: 'Elevation (m)',
                data: elevationData,
                borderColor: 'rgba(200, 200, 200, 0.5)',
                backgroundColor: 'rgba(200, 200, 200, 0.5)',
                fill: true,
                borderWidth: 1,
                pointRadius: 0,
                tension: 0.4,
                yAxisID: 'false',
            }
        ]
    },
    options: {
        responsive: true,
        scales: {
            x: {
                title: {
                    display: true,
                    text: 'Distance (km)'
                },
                ticks: {
                    callback: function(value, index, values) {
                        if (index % 41 === 0) {
                            return value / 41 + ' km';
                        }
                    }
                }
            },
            y: {
                title: {
                    display: true,
                    text: 'HR/Pace Ratio'
                }
            }
        },
        plugins: {
            tooltip: {
                callbacks: {
                    label: function(tooltipItem) {
                        return 'Ratio: ' + tooltipItem.raw;
                    }
                }
            },
            legend: {
                display: false,
                position: 'top'
            }
        }
    }
});
paceChart.data.datasets.push({
    label: 'Wysokość (m)',
    data: elevationData,
    backgroundColor: 'rgba(200, 200, 200, 0.5)',
    borderWidth: 0,
    type: 'line',
    pointRadius: 0,
    fill: true,
    yAxisID: 'elevationAxis',
    hidden: !elevationVisible
});
