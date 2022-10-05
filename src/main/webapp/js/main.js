var username=new URL(location.href).searchParams.get("username");
var user;

$(document).ready(function (){
    $(function (){
        
        $('[data-toggle="tooltip"]').tooltip();  
    });
    getUsuario().then(function (){
        $("#mi-perfil-btn").attr("href","profile.html?username="+username);
        $("#user-saldo").html(user.saldo.toFixed(2)+"$");
        getEquipo(false, "ASC");
        $("#ordenar-genero").click(ordenarEquipos);     
    });
});
async function getUsuario(){
    await $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletUsuarioPedir",
        data: $.param({
            username: username
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);
            if (parsedResult != false) {
                user=parsedResult;    
            } else {
               console.log("Error recuperando los datos del usuario");
            }
        }
    });  
}

function getEquipo(ordenar,orden){
    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletEquiposListar",
        data: $.param({
            ordenar: ordenar,
            orden:orden
        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);
            if (parsedResult != false) {
                mostrarEquipo(parsedResult);
            } else {
               console.log("Error recuperando los datos de los equipos");
            }
        }
    });
}
function mostrarEquipo(equipos) {
    let contenido = "";
    $.each(equipos, function (index, equipos) {
        equipos = JSON.parse(equipos);
        let precio;

        if (equipos.equipos_disponibles > 0) {
console.log(equipos.equipos_disponibles)
            if (user.premium) {

                if (equipos.novedad) {
                    precio = (2 - (2 * 0.1));
                } else {
                    precio = (1 - (1 * 0.1));
                }
            } else{
                 if (equipos.novedad) {
                     precio=2;                     
                 }else{                     
                      precio=1;                     
                 }                               
            }
            contenido += '<tr><th scope="row">' + equipos.id + '</th>' +
                    '<td>' + equipos.descripcion +'</td>' +
                    '<td>' + equipos.tipo + '</td>' +
                    '<td>' + equipos.marca + '</td>' +
                    '<td>' + equipos.equipos_disponibles + '</td>' +
                    '<td><input type="checkbox" name="novedad" id="novedad' + equipos.id + '" disabled ';
            if (equipos.novedad) {
                contenido += 'checked';
            }
            contenido += '></td>' +
                    '<td>' + precio + '</td>' +
                    '<td><button onclick="alquilarEquipo(' + equipos.id + ',' + precio + ');" class="btn btn-success" ';
            if (user.saldo < precio) {
                contenido += ' disabled ';
            }
            contenido += '>Reservar</button></td></tr>'
        }
    });
    $("#equipos-tbody").html(contenido);
}
function ordenarEquipos() {

    if ($("#icono-ordenar").hasClass("fa-sort")) {
        getEquipo(true, "ASC");
        $("#icono-ordenar").removeClass("fa-sort");
        $("#icono-ordenar").addClass("fa-sort-down");
    } else if ($("#icono-ordenar").hasClass("fa-sort-down")) {
        getEquipo(true, "DESC");
        $("#icono-ordenar").removeClass("fa-sort-down");
        $("#icono-ordenar").addClass("fa-sort-up");
    } else if ($("#icono-ordenar").hasClass("fa-sort-up")) {
        getEquipo(false, "ASC");
        $("#icono-ordenar").removeClass("fa-sort-up");
        $("#icono-ordenar").addClass("fa-sort");
    }
}
function alquilarEquipo(id, precio) {

    $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletEquipoAlquilar",
        data: $.param({
            id: id,
            username: username

        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {
                restarDinero(precio).then(function () {
                    location.reload();
                })
            } else {
                console.log("Error en la reserva del Equipo");
            }
        }
    });
}


async function restarDinero(precio) {

    await $.ajax({
        type: "GET",
        dataType: "html",
        url: "./ServletUsuarioRestarDinero",
        data: $.param({
            username: username,
            saldo: parseFloat(user.saldo - precio)

        }),
        success: function (result) {
            let parsedResult = JSON.parse(result);

            if (parsedResult != false) {
                console.log("Saldo actualizado");
            } else {
                console.log("Error en el proceso de pago");
            }
        }
    });
}