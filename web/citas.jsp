<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Citas</title>
        <link rel="shortcut icon" href="img/Solo logo.ico">
        <link href="css/bootstrap.css" rel="stylesheet" type="text/css"/>
        <link href="css/styles.css" rel="stylesheet" type="text/css"/>

        <script src="js/jquery-3.4.1.min.js" type="text/javascript"></script>

        <script src="js/68ab40f8cf.js" type="text/javascript"></script>
        <link href="css/pacientes.css" rel="stylesheet" type="text/css"/>
        <script src="js/popper.min.js" type="text/javascript"></script>
        <!-- Tablas-->
        <link href="css/dataTables.bootstrap4.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/select.bootstrap4.min.css" rel="stylesheet" type="text/css"/>
        <link href="css/responsive.bootstrap4.min.css" rel="stylesheet" type="text/css"/>
        <script src="js/jquery.dataTables.min.js" type="text/javascript"></script>
        <script src="js/dataTables.bootstrap4.min.js" type="text/javascript"></script>
        <script src="js/dataTables.select.min.js" type="text/javascript"></script>

        <script src="js/jquery.dataTables.min.js" type="text/javascript"></script>
        <script src="js/dataTables.bootstrap4.min.js" type="text/javascript"></script>
        <script src="js/dataTables.responsive.min.js" type="text/javascript"></script>
        <script src="js/dataTables.bootstrap4.min.js" type="text/javascript"></script>
        <script src="js/responsive.bootstrap4.min.js" type="text/javascript"></script>
        <!-- Agregar Ventana modal-->
        <script src="js/bootstrap.min.js" type="text/javascript"></script>

        <script src="js/default.js" type="text/javascript"></script>

    </head>
    <body>
        <%@include file="menu.jsp" %>
        <div class="main-content">
            <div class="title d-flex  justify-content-between align-items-center">
                <h5>
                    DATOS DE LAS CITAS
                </h5>
                <button class="btn btn-primary" data-toggle="modal" data-target="#exampleModal" data-whatever="@mdo"><i class="fas fa-plus-circle" ></i> Nueva Cita</button>
            </div>
            <div class="main">
                <label for="cbMos">Mostrar</label>
                <select class="browser-default custom-select" id="cbMos">

                    <option selected value="1">Todas</option>
                    <option value="2">Pendientes</option>
                    <option value="3">Realizadas</option>
                </select>
            </div>
            <div class="main">
                <div class="modal fade" id="exampleModal" data-backdrop="static" data-keyboard="false" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                    <div class="modal-dialog" role="document">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel"><i class="fas fa-plus-circle" ></i> Nueva Cita</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>
                            <div class="modal-body">
                                <form>
                                    <div class="form-row">
                                        <div class="form-group col-md-6">
                                            <label for="dtCita">Fecha de Cita</label>
                                            <input type="date" id="dtCita" class="form-control"><br>
                                        </div>
                                        <div class=" form-group col-md-6">
                                            <label for="cbTipo">Doctor</label>
                                            <select class="browser-default custom-select" id="cbTipo">

                                                <option selected value="1">Dr. House</option>
                                                <option value="2">Dra. Ana María Polo</option>
                                            </select>
                                        </div>
                                    </div>


                                </form>
                                <p>
                                    <button class="btn btn-primary" type="button" data-toggle="collapse" data-target=".multi-collapse" aria-expanded="false" aria-controls="multiCollapseExample1 multiCollapseExample2">Nuevo Paciente</button>
                                </p>
                                <div class="collapse show multi-collapse" id="multiCollapseExample1">
                                    <table id="tb2" class="table table-striped table-bordered" style="width:100%">
                                        <thead>
                                            <tr>
                                                <th>Paciente</th>
                                                <th>Telefono</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <tr>
                                                <td>Juan Perez</td>
                                                <td>65656656</td>
                                            </tr>
                                            <tr>
                                                <td>Juan Perez</td>
                                                <td>65656656</td>
                                            </tr>
                                            <tr>
                                                <td>Juan Perez</td>
                                                <td>65656656</td>
                                            </tr>
                                            <tr>
                                                <td>Juan Perez</td>
                                                <td>65656656</td>
                                            </tr>
                                            <tr>
                                                <td>Juan Perez</td>
                                                <td>65656656</td>
                                            </tr>
                                            <tr>
                                                <td>Juan Perez</td>
                                                <td>65656656</td>
                                            </tr>
                                            <tr>
                                                <td>Juan Perez</td>
                                                <td>65656656</td>
                                            </tr>
                                            <tr>
                                                <td>Juan Perez</td>
                                                <td>65656656</td>
                                            </tr>
                                            <tr>
                                                <td>Juan Perez</td>
                                                <td>65656656</td>
                                            </tr>
                                            <tr>
                                                <td>Juan Perez</td>
                                                <td>65656656</td>
                                            </tr>
                                            <tr>
                                                <td>Juan Perez</td>
                                                <td>65656656</td>
                                            </tr>
                                            <tr>
                                                <td>Juan Perez</td>
                                                <td>65656656</td>
                                            </tr>

                                        </tbody>
                                        <tfoot>
                                        </tfoot>
                                    </table>
                                </div>

                                <div class="collapse multi-collapse" id="multiCollapseExample2">
                                    <div class="card card-body">
                                        <form>
                                            <div class="form-row">
                                                <div class="form-group col-md-6">
                                                    <label for="txtNombres">Nombres</label>
                                                    <input type="text" class="form-control" id="txtNom">
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="txtApe">Apellidos</label>
                                                    <input type="text" class="form-control" id="txtApe" >
                                                </div>
                                            </div>
                                            <div class="form-group">
                                                <label for="txtDir">Dirección</label>
                                                <input type="text" class="form-control" id="txtDir" >
                                            </div>
                                            <div class="form-row">
                                                <div class="form-group col-md-6">
                                                    <label for="txtTel1">Telefono 1</label>
                                                    <input type="text" class="form-control" id="txtTel1">
                                                </div>
                                                <div class="form-group col-md-6">
                                                    <label for="txtTel2">Telefono 2</label>
                                                    <input type="text" class="form-control" id="txtTel2">
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
                                            <button type="submit" class="btn btn-primary">Agregar</button>

                                        </form>
                                    </div>
                                </div>

                            </div>
                            <div class="modal-footer">
                                <button type="submit" class="btn btn-primary" data-dismiss="modal"><i class="fas fa-ban"></i> Cancelar</button>

                                <button type="submit" class="btn btn-primary"><i class="fas fa-edit"></i> Actualizar</button>

                                <button type="submit" class="btn btn-primary"><i class="fas fa-save"></i> Guardar</button>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="tbPac">

                    <table id="tb1" class="table table-striped table-bordered dt-responsive nowrap" style="width:100%">
                        <thead>
                            <tr>
                                <th>Paciente</th>
                                <th>Doctor</th>
                                <th>Clinica</th>
                                <th>Fecha</th>
                                <th>Opción</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td>Juan Perez</td>
                                <td>Dr. House</td>
                                <td>Cara Sucia</td>
                                <td>31/10/2019</td>
                                <td>
                        <center>
                            <button class="btn btn-primary"><i class="fas fa-pencil-alt"></i></button>
                            <button class="btn btn-danger"><i class="fas fa-trash-alt"></i></button>
                        </center>
                        </td>
                        </tr>
                        <tr>
                            <td>Juan Perez</td>
                            <td>Dr. House</td>
                            <td>Cara Sucia</td>
                            <td>31/10/2019</td>
                            <td>
                        <center>
                            <button class="btn btn-primary"><i class="fas fa-pencil-alt"></i></button>
                            <button class="btn btn-danger"><i class="fas fa-trash-alt"></i></button>
                        </center>
                        </td>
                        </tr>
                        <tr>
                            <td>Juan Perez</td>
                            <td>Dr. House</td>
                            <td>Cara Sucia</td>
                            <td>31/10/2019</td>
                            <td>
                        <center>
                            <button class="btn btn-primary"><i class="fas fa-pencil-alt"></i></button>
                            <button class="btn btn-danger"><i class="fas fa-trash-alt"></i></button>
                        </center>
                        </td>
                        </tr>
                        <tr>
                            <td>Juan Perez</td>
                            <td>Dr. House</td>
                            <td>Cara Sucia</td>
                            <td>31/10/2019</td>
                            <td>
                        <center>
                            <button class="btn btn-primary"><i class="fas fa-pencil-alt"></i></button>
                            <button class="btn btn-danger"><i class="fas fa-trash-alt"></i></button>
                        </center>
                        </td>
                        </tr>
                        <tr>
                            <td>Juan Perez</td>
                            <td>Dr. House</td>
                            <td>Cara Sucia</td>
                            <td>31/10/2019</td>
                            <td>
                        <center>
                            <button class="btn btn-primary"><i class="fas fa-pencil-alt"></i></button>
                            <button class="btn btn-danger"><i class="fas fa-trash-alt"></i></button>
                        </center>
                        </td>
                        </tr>
                        <tr>
                            <td>Juan Perez</td>
                            <td>Dr. House</td>
                            <td>Cara Sucia</td>
                            <td>31/10/2019</td>
                            <td>
                        <center>
                            <button class="btn btn-primary"><i class="fas fa-pencil-alt"></i></button>
                            <button class="btn btn-danger"><i class="fas fa-trash-alt"></i></button>
                        </center>
                        </td>
                        </tr>

                        </tbody>

                        <tfoot>
                            <tr>
                                <th>Paciente</th>
                                <th>Doctor</th>
                                <th>Clinica</th>
                                <th>Fecha</th>
                                <th>Opción</th>
                            </tr>
                        </tfoot>
                    </table>
                </div>
            </div>

        </div>
    </body>
</html>