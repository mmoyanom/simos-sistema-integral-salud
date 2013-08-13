Imports System.IO
Imports System.Configuration
Imports System.Globalization
Imports System.Text

Namespace util

    Public Class Log
        Public Shared Sub Append(ByVal logMessage As String)
            Dim dir As New DirectoryInfo(ConfigurationManager.AppSettings("LOG_PATH").ToString)
            If dir.Exists = False Then
                dir.Create()
            End If

            Using w As StreamWriter = File.AppendText(ConfigurationManager.AppSettings("LOG_PATH").ToString + DateTime.Now.ToString("yyyyMMdd", CultureInfo.InvariantCulture) + ".txt")
                w.WriteLine("{0} # {1}", DateTime.Now.ToLongTimeString(), logMessage)
            End Using

        End Sub

        'Public Shared Sub Print(ByVal logMessage As String, ByVal w As TextWriter)
        '    w.Write(vbCrLf + "Log Entry : ")
        '    w.WriteLine("{0} {1}", DateTime.Now.ToLongTimeString(), _
        '        DateTime.Now.ToLongDateString())
        '    w.WriteLine("  :")
        '    w.WriteLine("  :{0}", logMessage)
        '    w.WriteLine("-------------------------------")
        'End Sub
    End Class

End Namespace