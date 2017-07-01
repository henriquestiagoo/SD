import paramiko, sys, os

machines = [{
    "order": 1,
    "class": "configuration.ConfRun",
    "machine": "l040101-ws01.ua.pt"
  },
  {
    "order": 2,
    "class": "generalRepository.GeneralRepositoryRun",
    "machine": "l040101-ws03.ua.pt"
  },
  {
    "order": 3,
    "class": "assaultParty.AssaultPartyRun0",
    "machine": "l040101-ws04.ua.pt"
  },
  {
    "order": 4,
    "class": "assaultParty.AssaultPartyRun1",
    "machine": "l040101-ws05.ua.pt"
  },
  {
    "order": 5,
    "class": "controlAndCollectionSite.ControlAndCollectionSiteRun",
    "machine": "l040101-ws07.ua.pt"
  },
  {
    "order": 6,
    "class": "concentrationSite.ConcentrationSiteRun",
    "machine": "l040101-ws09.ua.pt"
  },
  {
    "order": 7,
    "class": "museum.MuseumRun",
    "machine": "l040101-ws10.ua.pt"
  },
  {
    "order": 8,
    "class": "main.OrdinaryThiefRun",
    "machine": "l040101-ws10.ua.pt"
  },
  {
    "order": 9,
    "class": "main.MasterThiefRun",
    "machine": "l040101-ws10.ua.pt"
  }]

COMMAND = 'java -cp %s:libs/* %s'
USERNAME = "sd0103"
PASSWORD = "tigasmigas"

def sendFileToMachines():
    for s in machines:
        ssh.connect(s['machine'], username=USERNAME, password=PASSWORD)
        sftp = ssh.open_sftp()
        sftp.chdir("/home/sd0103/")
        sftp.put(os.getcwd() + "/"+FILENAME, FILENAME)
        sftp.put(os.getcwd() + "/"+'conf.xml', 'conf.xml')
        print ("Sending the .jar to the machine: " + s['machine'])
    ssh.close()

def executeServices():
    grService = None
    for s in machines:
        ssh.connect(s['machine'], username=USERNAME, password=PASSWORD)
        stdin, stdout, stderr = ssh.exec_command(COMMAND % ( FILENAME, s['class']))
        classname = s['class'].split('.')[-1]
        if classname == 'GeneralRepositoryRun':
          grService = stdout.channel
        print("Executing %s in %s" % (classname, s['machine']))

    print('Waiting for simulation to end...')
    if grService.recv_exit_status() == 0:
      print('Simulation has ended successfuly!!')
    ssh.close()

def killServices():
  pass

if __name__ == '__main__':
    FILENAME = sys.argv[1]
    ssh = paramiko.SSHClient()
    ssh.load_system_host_keys()
    ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())

    sendFileToMachines()
    executeServices()
    print('End Operations!!')
