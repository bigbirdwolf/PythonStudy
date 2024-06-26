from socket import *
import struct
from functools import partial

ip_port = ('127.0.0.1',8080)
back_log = 5
buffer_size = 1024

tcp_client = socket(AF_INET,SOCK_STREAM)
tcp_client.connect(ip_port)

while True:
    cmd = input('>>: ').strip()
    if not cmd: continue
    if cmd == 'quit':break

    tcp_client.send(cmd.encode('utf-8'))

    #解决粘包问题
    length = tcp_client.recv(4)
    length = struct.unpack('i', length)[0]

    recv_szie = 0
    recv_data = b''
    while recv_szie < length:
        recv_data += tcp_client.recv(buffer_size)
        recv_szie = len(recv_data)
    print('命令的执行结果是:',recv_data.decode('gbk'))
tcp_client.close()