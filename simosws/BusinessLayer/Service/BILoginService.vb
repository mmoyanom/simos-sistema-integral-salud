Imports simosws
Imports simosws.DTO

Namespace Service
    Public Interface BILoginService

        Function Authenticate(ByVal UserLogin As String, ByVal Password As String) As LoginResponse


    End Interface
End Namespace
