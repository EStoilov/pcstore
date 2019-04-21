(()=> {
    fetch('/addresses/fetch')
        .then((response) => response.json())
        .then((json) => {
            json.forEach((address) =>
                $('#user-addresses')
                    .append(`<option value="${address.id}">${address.city} ${address.street} ${address.number}</option>`));
        })
        .catch((err) => console.log(err));
})();