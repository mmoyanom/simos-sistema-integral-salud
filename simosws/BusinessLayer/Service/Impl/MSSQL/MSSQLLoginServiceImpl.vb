Imports simosws.Connection
Imports simosws.DTO
Imports System.Data.SqlClient

Namespace Service.Impl

    Class MSSQLLoginServiceImpl : Implements BILoginService

        Public Function Authenticate(ByVal UserLogin As String, ByVal Password As String) As LoginResponse Implements BILoginService.Authenticate
            Dim response As New LoginResponse
            Dim con As SqlConnection = Nothing
            Try
                con = SimosDBConnection.GetConnection
                con.Open()

                Dim cmd As New SqlCommand
                cmd.Connection = con
                cmd.CommandType = CommandType.StoredProcedure
                cmd.CommandText = ConfigurationManager.AppSettings("SP_LOGIN").ToString
                cmd.Parameters.Add("@ploginusuario", SqlDbType.VarChar).Value = UserLogin
                cmd.Parameters.Add("@pclaveacceso", SqlDbType.VarChar).Value = Password
                cmd.Parameters.Add("@pcodigosistema", SqlDbType.VarChar).Value = "GES"

                Dim reader = cmd.ExecuteReader
                If reader.HasRows Then
                    While reader.Read
                        response.SystemId = reader.GetString(0)
                        response.ModuleId = reader.GetString(1)
                        response.Message = reader.GetString(2)
                        response.UserLogin = reader.GetString(3)
                        response.UserId = reader.GetInt32(4)
                    End While
                End If

            Catch ex As Exception
                util.Log.Append(ex.Message)
                response = Nothing
            Finally
                If con IsNot Nothing Then
                    con.Close()
                End If
            End Try

            Return response

        End Function

    End Class
End Namespace
