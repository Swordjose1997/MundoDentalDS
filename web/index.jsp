<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0"><!-- Latest compiled and minified CSS -->
        <link href="css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/styles.css" rel="stylesheet" type="text/css"/>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/jquery-3.4.1.min.js"></script>
        <script src="js/bootstrap.bundle.min.js" type="text/javascript"></script>
    </head>
    <body>
        <div id="sidebar">
            <%@include file="html/menu.jsp" %>
        </div>
        <div id="rightSideWrapper">
            <header> 
                Header 
            </header>

            <div class="ContentBox"><!--. poner en minusculas.-->
                <main>
                    <div class="d-flex justify-content-between">
                        <div>
                            <h3>Datos del Paciente</h3>
                        </div>
                        <button class="btn btn-primary" type="button" data-toggle="collapse" data-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
                            Agregar Paciente hola
                        </button>
                    </div>

                    <div class="d-flex justify-content-center">
                        <div class="collapse " id="collapseExample">
                            <div class="card card-body">
                                <form>
                                    <div class="form-row">
                                        <div class="form-group col-md-6">
                                            <label for="txtNombres">Nombres</label>
                                            <input type="text" class="form-control" id="txtNom">
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label for="txtApe">Apellidos</label>
                                            <input type="txt" class="form-control" id="txtApe" >
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="txtDir">Dirección</label>
                                        <input type="text" class="form-control" id="txtDir" >
                                    </div>
                                    <div class="form-row">
                                        <div class="form-group col-md-6">
                                            <label for="txtNombres">Telefono 1</label>
                                            <input type="text" class="form-control" id="txtNom">
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label for="txtApe">Telefono 2</label>
                                            <input type="txt" class="form-control" id="txtApe">
                                        </div>
                                    </div>
                                    <div class="form-group">

                                        <label for="txtFecha">Fecha de nacimiento</label>
                                        <input type="date" id="txtFecha" class="form-control"><br>
                                    </div>
                                    <div class="form-group">
                                        <label for="txtEmail">Email</label>
                                        <input type="text" class="form-control" id="txtEmail" >
                                    </div>
                                    <button type="submit"   data-toggle="collapse" data-target="#collapseExample" class="btn btn-primary">Agregar</button>

                                </form>
                            </div>
                        </div>
                    </div>
                    <table class="table table-bordered">
                        <thead>
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">First</th>
                                <th scope="col">Last</th>
                                <th scope="col">Handle</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <th scope="row">1</th>
                                <td>Mark</td>
                                <td>Otto</td>
                                <td>@mdo</td>
                            </tr>
                            <tr>
                                <th scope="row">2</th>
                                <td>Jacob</td>
                                <td>Thornton</td>
                                <td>@fat</td>
                            </tr>
                            <tr>
                                <th scope="row">3</th>
                                <td colspan="2">Larry the Bird</td>
                                <td>@twitter</td>
                            </tr>
                        </tbody>
                    </table>
                </main>

                <section>
                    Section Content
                </section>
                <footer> 
                    Footer 
                </footer>
            </div>
        </div>
    </div>

</body>
</html>

