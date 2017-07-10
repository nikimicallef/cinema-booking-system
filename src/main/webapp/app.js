var app = angular.module('app', []);

app.service('BookingsService', ['$http', function ($http) {
    this.getAllFilms = function getAllFilms() {
        return $http({
            method: 'GET',
            url: 'films'
        });
    };

    this.getShowingsForFilm = function getShowingsForFilm(filmId) {
        return $http({
            method: 'GET',
            url: 'films/' + filmId + "/showings"
        });
    };

    this.getBookingsForShowings = function getBookingsForShowings (showingId) {
        return $http({
            method: 'GET',
            url: 'showings/' + showingId
        });
    };

    this.getTheatreInformation = function getTheatreInformation(theatreId) {
        return $http({
            method: 'GET',
            url: 'theatre/' + theatreId
        });
    };

    this.createBooking = function createBooking(filmShowingId, seatRow, seatColumn) {
        return $http({
            method: 'POST',
            url: 'booking',
            data: {filmShowingId:filmShowingId, newBookings:[{seatRow:seatRow, seatColumn:seatColumn}]}
        });
    };
}]);

app.controller('BookingsController', ['$scope', 'BookingsService',
    function ($scope, BookingsService) {
        $scope.getAllFilms = function () {
            BookingsService.getAllFilms()
                .then(function success(response) {
                        $scope.films = response.data.films;
                        $scope.errorMessage = '';
                    },
                    function error(response) {
                        if (response.status === 404) {
                            $scope.errorMessage = 'Not found';
                        }
                        else {
                            $scope.errorMessage = "Error getting films. Please try again later.";
                        }
                    });
        };

        $scope.getShowingsForFilm = function () {
            $scope.theatreInformation = null;
            $scope.seatAvailable = null;
            $scope.chosenSeatRow = null;
            $scope.chosenSeatColumn = null;
            $scope.bookingSuccess = null;

            for(var cnt = 0; cnt < $scope.films.length; cnt++){
                // It is assumed that you can not have two films with the same name
                if($scope.films[cnt].filmName === $scope.myFilm.chosenFilmName){
                    $scope.chosenFilmId = $scope.films[cnt].id;
                    break;
                }
            }
            BookingsService.getShowingsForFilm($scope.chosenFilmId)
                .then(function success(response) {
                        $scope.showings = response.data.showings;
                        $scope.errorMessage = '';
                    },
                    function error(response) {
                        if (response.status === 404) {
                            $scope.errorMessage = 'Not found';
                        }
                        else {
                            $scope.errorMessage = 'Error getting showings for film with id ' + $scope.chosenFilmId;
                        }
                    });
        };

        $scope.getBookingsForShowing = function () {
            $scope.seatAvailable = null;
            $scope.chosenSeatRow = null;
            $scope.chosenSeatColumn = null;
            $scope.bookingSuccess = null;

            for(var cnt = 0; cnt < $scope.showings.length; cnt++){
                // It is assumed that you can not have two showings for that film with the same date and time
                if($scope.showings[cnt].date_time === $scope.myShowing.chosenDateTime){
                    $scope.chosenShowingId = $scope.showings[cnt].showing_id;
                    break;
                }
            }
            BookingsService.getBookingsForShowings($scope.chosenShowingId)
                .then(function success(response) {
                        $scope.chosenTheatreId = response.data.theatre_id;
                        $scope.bookings = response.data.bookings;
                        $scope.errorMessage = '';
                        $scope.getTheatreInformation($scope.chosenTheatreId);
                    },
                    function error(response) {
                        if (response.status === 404) {
                            $scope.errorMessage = 'Not found';
                        }
                        else {
                            $scope.errorMessage = 'Error getting bookings for showing with id ' + $scope.chosenShowingId;
                        }
                    });
        };

        $scope.getTheatreInformation = function () {
            BookingsService.getTheatreInformation($scope.chosenTheatreId)
                .then(function success(response) {
                        $scope.theatreRows = response.data.rows;
                        $scope.theatreColumns = response.data.columns;
                        $scope.theatreInformation = 'This theatre has ' + $scope.theatreRows + ' rows and ' + $scope.theatreColumns + ' columns. Please select the seat you want to book.';
                        $scope.errorMessage = '';
                    },
                    function error(response) {
                        $scope.theatreString = '';
                        if (response.status === 404) {
                            $scope.errorMessage = 'Not found';
                        }
                        else {
                            $scope.errorMessage = 'Error getting theatre information for theatre with id ' + $scope.chosenTheatreId;
                        }
                    });
        };

        $scope.checkIfSeatIsAlreadyBooked = function() {
            var row = $scope.chosenSeatRow;
            var column = $scope.chosenSeatColumn;
            $scope.bookingSuccess = null;

            if(row === null || row === undefined || row.length === 0 || column === null || column === undefined || column.length === 0){
                $scope.seatAvailable = null;
                $scope.errorMessage = '';
            } else if(row > $scope.theatreRows || row < 1 || column > $scope.theatreColumns || column < 1){
                $scope.seatAvailable = null;
                $scope.errorMessage = 'Seat with row ' + row + ' and column ' + column + ' is not valid.';
            } else {
                $scope.errorMessage = '';
                $scope.seatAvailable = true;
                for(var cnt = 0; cnt < $scope.bookings.length; cnt++){
                    if(row === $scope.bookings[cnt].seat_row && column === $scope.bookings[cnt].seat_column){
                        $scope.seatAvailable = false;
                        break;
                    }
                }
            }
        };

        //TODO: Solve the below
        // Potential security issue where people can invoke this method via debugger and the request will still go through even though the book button does not show
        //Can skip certain validations who are done before actually showing the book button
        $scope.createBooking = function() {
            var row = $scope.chosenSeatRow;
            var column = $scope.chosenSeatColumn;
            var showingId = $scope.chosenShowingId;

            BookingsService.createBooking(showingId, row, column)
                .then(function success(response) {
                        $scope.bookingSuccess = 'Booking success! Your booking number is ' + response.data.bookingIds[0];
                        $scope.errorMessage = '';
                        $scope.seatAvailable = false;
                    },
                    function error(response) {
                        $scope.theatreString = '';
                        if (response.status === 404) {
                            $scope.errorMessage = 'Not found';
                        }
                        else {
                            $scope.errorMessage = 'Booking failure. Please try again';
                        }
                    });
        };
    }
]);