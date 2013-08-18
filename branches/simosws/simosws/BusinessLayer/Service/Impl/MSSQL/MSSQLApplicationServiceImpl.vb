Imports simosws.Connection
Imports simosws.DTO
Imports System.Data.SqlClient

Namespace Service.Impl

    Public Class MSSQLApplicationServiceImpl : Implements BIApplicationService

        Public Function GetDocumentTypes() As List(Of DocumentType) Implements BIApplicationService.GetDocumentTypes
            Dim items As List(Of DocumentType) = Nothing
            Dim con As SqlConnection = Nothing
            Try
                con = SimosDBConnection.GetConnection()
                con.Open()

                Dim cmd As New SqlCommand
                cmd.Connection = con
                cmd.CommandType = CommandType.StoredProcedure
                cmd.CommandText = ConfigurationManager.AppSettings("SP_TIPODOCIDENTIDAD").ToString()

                Dim reader = cmd.ExecuteReader()
                If reader.HasRows Then
                    items = New List(Of DocumentType)
                    While reader.Read()
                        Dim x As New DocumentType
                        x.Id = reader.GetInt32(0)
                        x.Description = reader.GetString(1)
                        items.Add(x)
                    End While
                End If
            Catch ex As Exception
                items = Nothing
                util.Log.Append(ex.Message)
            Finally
                If con IsNot Nothing Then
                    con.Close()
                End If
            End Try
            Return items
        End Function

        Public Function GetPaymentConcepts() As List(Of PaymentConcept) Implements BIApplicationService.GetPaymentConcepts
            Dim items As List(Of PaymentConcept) = Nothing
            Dim con As SqlConnection = Nothing
            Try
                con = SimosDBConnection.GetConnection()
                con.Open()

                Dim cmd As New SqlCommand
                cmd.Connection = con
                cmd.CommandType = CommandType.StoredProcedure
                cmd.CommandText = ConfigurationManager.AppSettings("SP_CONCEPTOPAGO").ToString()

                Dim reader = cmd.ExecuteReader()
                If reader.HasRows Then
                    items = New List(Of PaymentConcept)
                    While reader.Read()
                        Dim x As New PaymentConcept
                        x.Id = Integer.Parse(reader.GetByte(0))
                        x.Description = reader.GetString(1)
                        items.Add(x)
                    End While
                End If
            Catch ex As Exception
                items = Nothing
                util.Log.Append(ex.Message)
            Finally
                If con IsNot Nothing Then
                    con.Close()
                End If
            End Try
            Return items
        End Function

        Public Function GetLocations() As List(Of Location) Implements BIApplicationService.GetLocations
            Dim items As List(Of Location) = Nothing
            Dim con As SqlConnection = Nothing
            Try
                util.Log.Append("entrando en getlocations")

                con = SimosDBConnection.GetConnection()
                con.Open()

                Dim cmd As New SqlCommand
                cmd.Connection = con
                cmd.CommandType = CommandType.StoredProcedure
                cmd.CommandText = ConfigurationManager.AppSettings("SP_LUGARPAGO").ToString()
                util.Log.Append("entrando en getlocations")
                Dim reader = cmd.ExecuteReader()
                If reader.HasRows Then
                    items = New List(Of Location)
                    While reader.Read()
                        Dim x As New Location
                        x.Id = Integer.Parse(reader.GetByte(0))
                        x.Description = reader.GetString(1)
                        items.Add(x)
                    End While
                End If
            Catch ex As Exception
                items = Nothing
                util.Log.Append(ex.Message)
            Finally
                If con IsNot Nothing Then
                    con.Close()
                End If
            End Try
            Return items
        End Function

        Public Function GetDeliveryModes() As List(Of DeliveryMode) Implements BIApplicationService.GetDeliveryModes
            Dim items As List(Of DeliveryMode) = Nothing
            Dim con As SqlConnection = Nothing
            Try
                con = SimosDBConnection.GetConnection()
                con.Open()

                Dim cmd As New SqlCommand
                cmd.Connection = con
                cmd.CommandType = CommandType.StoredProcedure
                cmd.CommandText = ConfigurationManager.AppSettings("SP_MODOENTREGA").ToString()

                Dim reader = cmd.ExecuteReader()
                If reader.HasRows Then
                    items = New List(Of DeliveryMode)
                    While reader.Read()
                        Dim x As New DeliveryMode
                        x.Id = Integer.Parse(reader.GetByte(0))
                        x.Description = reader.GetString(1)
                        items.Add(x)
                    End While
                End If
            Catch ex As Exception
                items = Nothing
                util.Log.Append(ex.Message)
            Finally
                If con IsNot Nothing Then
                    con.Close()
                End If
            End Try
            Return items
        End Function

        Public Function GetStaffIndicatingPayments() As List(Of StaffIndicatingPayment) Implements BIApplicationService.GetStaffIndicatingPayments
            Dim items As List(Of StaffIndicatingPayment) = Nothing
            Dim con As SqlConnection = Nothing
            Try
                con = SimosDBConnection.GetConnection()
                con.Open()

                Dim cmd As New SqlCommand
                cmd.Connection = con
                cmd.CommandType = CommandType.StoredProcedure
                cmd.CommandText = ConfigurationManager.AppSettings("SP_PERSONAINDICAPAGO").ToString()

                Dim reader = cmd.ExecuteReader()
                If reader.HasRows Then
                    items = New List(Of StaffIndicatingPayment)
                    While reader.Read()
                        Dim x As New StaffIndicatingPayment
                        x.Id = Integer.Parse(reader.GetByte(0))
                        x.Description = reader.GetString(1)
                        items.Add(x)
                    End While
                End If
            Catch ex As Exception
                items = Nothing
                util.Log.Append(ex.Message)
            Finally
                If con IsNot Nothing Then
                    con.Close()
                End If
            End Try
            Return items
        End Function

        Public Function GetRespuestas() As System.Collections.Generic.List(Of Respuesta) Implements BIApplicationService.GetRespuestas
            Dim items As New List(Of Respuesta)
            Dim con As SqlConnection = Nothing
            Try
                con = SimosDBConnection.GetConnection()
                con.Open()

                Dim cmd As New SqlCommand
                cmd.Connection = con
                cmd.CommandType = CommandType.StoredProcedure
                cmd.CommandText = ConfigurationManager.AppSettings("SP_RESPUESTAS").ToString()

                Dim reader = cmd.ExecuteReader()
                If reader.HasRows Then
                    While reader.Read()
                        Dim x As New Respuesta
                        x.Descripcion = reader.GetString(0)
                        x.RespuestaId = reader.GetInt32(1)
                        x.PreguntaId = reader.GetInt32(2)
                        items.Add(x)
                    End While
                End If
            Catch ex As Exception
                items = Nothing
                util.Log.Append(ex.Message)
            Finally
                If con IsNot Nothing Then
                    con.Close()
                End If
            End Try

            Return items
        End Function

        Public Function GetAsignaciones(ByVal usuarioId As String, ByVal fecha As String) As System.Collections.Generic.List(Of Asignacion) Implements BIApplicationService.GetAsignaciones
            Dim items As New List(Of Asignacion)
            Dim con As SqlConnection = Nothing
            Try
                con = SimosDBConnection.GetConnection()
                con.Open()

                Dim cmd As New SqlCommand
                cmd.Connection = con
                cmd.CommandType = CommandType.StoredProcedure
                cmd.CommandText = ConfigurationManager.AppSettings("SP_ASIGNACIONES_X_USUARIO").ToString()
                cmd.Parameters.Add("@PROG_I_ID_USUARIOENCUESTA", SqlDbType.Int).Value = usuarioId.Trim()

                Dim provider As Globalization.CultureInfo = Globalization.CultureInfo.InvariantCulture
                cmd.Parameters.Add("@FECHA", SqlDbType.Date).Value = Date.ParseExact(fecha, "yyyyMMdd", provider)

                Dim reader = cmd.ExecuteReader()
                If reader.HasRows Then
                    While reader.Read()
                        Dim x As New Asignacion
                        x.FechaProgramada = reader.GetDateTime(0).ToString("yyyyMMdd")
                        x.EncuestaId = reader.GetInt32(1)
                        x.EstablecimientoId = reader.GetString(2)
                        x.EstablecimientoName = reader.GetString(3)
                        x.FechaProgramacion = reader.GetDateTime(4).ToString("yyyyMMdd")
                        x.TurnoId = Integer.Parse(reader.GetByte(5))
                        x.TurnoDescripcion = reader.GetString(6)
                        items.Add(x)
                    End While
                End If

            Catch ex As Exception
                items = Nothing
                util.Log.Append(ex.Message)
            Finally
                If con IsNot Nothing Then
                    con.Close()
                End If
            End Try
            Return items
        End Function

        Public Function SetRespuestasEncuestas(ByVal rptasEncs As List(Of RespuestaEncuesta)) As Integer Implements BIApplicationService.SetRespuestasEncuestas
            Dim resul As Integer
            Dim con As SqlConnection = Nothing
            Try
                con = SimosDBConnection.GetConnection()
                con.Open()

                Dim cmd As New SqlCommand
                cmd.Connection = con
                cmd.CommandType = CommandType.StoredProcedure
                cmd.CommandText = ConfigurationManager.AppSettings("SP_INSERTARPTAS").ToString()

                For Each rptaEnc As RespuestaEncuesta In rptasEncs
                    cmd.Parameters.Add("@RPTA_I_ID_OPCIONESRESPUESTA", SqlDbType.Int).Value = rptaEnc.OprespuestaId
                    cmd.Parameters.Add("@RPTA_I_ID_ENCUESTADO", SqlDbType.Int).Value = rptaEnc.EncuestadoId
                    cmd.Parameters.Add("@RPTA_I_ID_RESPUESTAPADRE", SqlDbType.Int).Value = rptaEnc.RespuestaPadreId
                    cmd.Parameters.Add("@RPTA_V_TEXTORESPUESTA", SqlDbType.VarChar).Value = rptaEnc.RespuestaTexto
                    cmd.Parameters.Add("@RPTA_N_RESPUESTA", SqlDbType.Decimal).Value = rptaEnc.RespuestaNum
                    cmd.Parameters.Add("@RPTA_I_V_MEDICAMENTOINSUMO", SqlDbType.VarChar).Value = rptaEnc.Medins
                    cmd.Parameters.Add("@RPTA_I_ID_RESPUESTA", SqlDbType.Int).Direction = ParameterDirection.Output
                    cmd.ExecuteScalar()
                    resul += cmd.Parameters("@RPTA_I_ID_RESPUESTA").Value

                Next
            Catch ex As Exception
                util.Log.Append(ex.Message)
            Finally
                If con IsNot Nothing Then
                    con.Close()
                End If
            End Try
            Return resul
        End Function
    End Class
End Namespace