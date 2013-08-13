Namespace DTO
    Public Class LoginResponse

        Private _systemId As String
        Public Property SystemId() As String
            Get
                Return _systemId
            End Get
            Set(ByVal value As String)
                _systemId = value
            End Set
        End Property

        Private _moduleId As String
        Public Property ModuleId() As String
            Get
                Return _moduleId
            End Get
            Set(ByVal value As String)
                _moduleId = value
            End Set
        End Property

        Private _message As String
        Public Property Message As String
            Get
                Return _message
            End Get
            Set(ByVal value As String)
                _message = value
            End Set
        End Property

        Private _userLogin As String
        Public Property UserLogin As String
            Get
                Return _userLogin
            End Get
            Set(ByVal value As String)
                _userLogin = value
            End Set
        End Property

        Private _userId As String
        Public Property UserId As String
            Get
                Return _userId
            End Get
            Set(ByVal value As String)
                _userId = value
            End Set
        End Property

    End Class

End Namespace