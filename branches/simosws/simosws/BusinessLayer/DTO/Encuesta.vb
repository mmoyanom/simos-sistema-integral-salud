Public Class Encuestado


    Private _id As Integer
    Public Property id() As Integer
        Get
            Return _id
        End Get
        Set(ByVal value As Integer)
            _id = value
        End Set
    End Property


    Private _encuestaGrupo As Integer
    Public Property encuestaGrupo() As Integer
        Get
            Return _encuestaGrupo
        End Get
        Set(ByVal value As Integer)
            _encuestaGrupo = value
        End Set
    End Property


    Private _nroCuestionario As String
    Public Property nroCuestionario() As String
        Get
            Return _nroCuestionario
        End Get
        Set(ByVal value As String)
            _nroCuestionario = value
        End Set
    End Property


    Private _created As Date
    Public Property created() As Date
        Get
            Return _created
        End Get
        Set(ByVal value As Date)
            _created = value
        End Set
    End Property


    Private _formularioId As String
    Public Property formularioId() As String
        Get
            Return _formularioId
        End Get
        Set(ByVal value As String)
            _formularioId = value
        End Set
    End Property



End Class
