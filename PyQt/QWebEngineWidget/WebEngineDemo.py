import sys

from PyQt5.QtCore import QUrl
from PyQt5.QtWidgets import QApplication, QMainWindow, QVBoxLayout, QWidget
from PyQt5.QtWebEngineWidgets import QWebEngineView
from PyQt5.QtGui import *

class WebEngineViewDemo(QMainWindow):
    def __init__(self):
        super().__init__()

        self.setWindowTitle('QtWebEngineView Demo')
        self.setGeometry(100, 100, 800, 600)


        # 创建一个 QWebEngineView 实例
        webview = QWebEngineView()
        webview.load(QUrl('https://www.baidu.com'))  # 加载网页
        self.setCentralWidget(webview)


if __name__ == '__main__':
    app = QApplication(sys.argv)
    window = WebEngineViewDemo()
    window.show()
    sys.exit(app.exec_())
