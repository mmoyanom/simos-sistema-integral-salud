Public Class Respuesta

    Private _oprespuestaId As Integer
    Public Property opcionRespuestaId() As Integer
        Get
            Return _oprespuestaId
        End Get
        Set(ByVal value As Integer)
            _oprespuestaId = value
        End Set
    End Property

    Private _encuestadoId As Integer
    Public Property encuestadoId() As Integer
        Get
            Return _encuestadoId
        End Get
        Set(ByVal value As Integer)
            _encuestadoId = value
        End Set
    End Property

    Private _respuestaId As Integer
    Public Property respuestaId() As Integer
        Get
            Return _respuestaId
        End Get
        Set(ByVal value As Integer)
            _respuestaId = value
        End Set
    End Property

    Private _respuestaPadreId As Integer
    Public Property respuestaParentId() As Integer
        Get
            Return _respuestaPadreId
        End Get
        Set(ByVal value As Integer)
            _respuestaPadreId = value
        End Set
    End Property

    Private _respuestaTexto As String
    Public Property respuestaTexto() As String
        Get
            Return _respuestaTexto
        End Get
        Set(ByVal value As String)
            _respuestaTexto = value
        End Set
    End Property

    Private _respuestaNum As Double
    Public Property respuestaNumero() As Double
        Get
            Return _respuestaNum
        End Get
        Set(ByVal value As Double)
            _respuestaNum = value
        End Set
    End Property


    Private _preguntaId As String
    Public Property preguntaId() As String
        Get
            Return _preguntaId
        End Get
        Set(ByVal value As String)
            _preguntaId = value
        End Set
    End Property


    Private _medins As String
    Public Property prescripcionId() As String
        Get
            Return _medins
        End Get
        Set(ByVal value As String)
            _medins = value
        End Set
    End Property


    Private _child As List(Of Respuesta)
    Public Property child() As List(Of Respuesta)
        Get
            Return _child
        End Get
        Set(ByVal value As List(Of Respuesta))
            _child = value
        End Set
    End Property

End Class
