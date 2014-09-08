require 'optparse'
require 'ostruct'
options = OpenStruct.new
options.tf = String
options.td = String
options.to = String
OptionParser.new do |opts|
  opts.banner = "Usage: RubyFile.rb [options]"

	opts.on( '-t', '--tracesFile FILE', 'set tracesFile' ) do |t|
    	options.tf = t
   	end

	opts.on( '-d', '--tracesDirectory DIR', 'set tracesDirectory' ) do |d|
    	options.td = d
   	end

    opts.on( '-o', '--outputFileDirectory DIR2', 'set outputFileDirectory' ) do |o|
    	options.to = o
   	end

end.parse!

TRACES_FILE = options.tf
TRACES_DIR  = options.td
OUTPUT_DIR  = options.to

Connection = Struct.new(:targetActor, :targetPort, :sourceActor, :sourcePort, :fifoSize , :remainingTokens, :fifoStatus)

OUT_FILE = File.new(OUTPUT_DIR + "fifoStatus.html", "w")
OUT_FILE.write(
'<html lang="fr"><head>
<meta http-equiv="content-type" content="text/html; charset=windows-1252">
  <style type="text/css">
      body
      {
          line-height: 1.6em;
      }
      #rounded-corner
      {
          font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
          font-size: 12px;
          margin: 45px;
          width: 650px;
          text-align: left;
          border-spacing: 0px 10px;
          rules
      }
      #rounded-corner thead td.left
      {
          background: #b9c9fe;
          border-radius: 20p;
          -moz-border-radius-topleft: 10px;
          border-top-left-radius: 10px;
          -moz-border-radius-bottomleft: 10px;
          border-bottom-left-radius: 10px;
      }
      #rounded-corner thead td.right
      {
          background: #b9c9fe;
          -moz-border-radius-topright: 10px;
          border-top-right-radius: 10px;
          -moz-border-radius-bottomright: 10px;
          border-bottom-right-radius: 10px;
     }
      #rounded-corner td
      {
          padding: 8px;
          font-weight: normal;
          font-size: 13px;
          color: #039;
          background: #b9c9fe;
          text-align: center;
          white-space:nowrap;
          font-weight : bold;
      }
      #rounded-corner td.connection
      {
          background: #FFFFFF;
          border-top: 1px solid #fff;
          font-weight : bold;
          color: green;
          vertical-align:top;
      }
      #rounded-corner td.warning
      {
          background: #FFFFFF;
          border-top: 1px solid #fff;
          font-weight : bold;
          color: orange;
          vertical-align:top;
      }
      #rounded-corner td.blocked
      {
          background: #FFFFFF;
          border-top: 1px solid #fff;
          font-weight : bold;
          color: red;
          vertical-align:top;
      }
  </style>
  <body>
       <table id="rounded-corner">
          <thead>')

File.readlines(TRACES_FILE).each do |line|

	line.chomp!
	orccArgs = line.split(" ")
	target = orccArgs[0]
	source = orccArgs[1]
	target_file = File.open(TRACES_DIR + target, "r")
	targetFifoContent = target_file.readlines.size
	#puts targetFifoContent
	source_file = File.open(TRACES_DIR + source, "r")
  	sourceFifoContent = source_file.readlines.size
  	#puts sourceFifoContent
  	fifoContent = sourceFifoContent - targetFifoContent
  	#puts fifoContent

  	if fifoContent == 0 then 
  		status = "warning"
  	elsif fifoContent >= orccArgs[6].to_i then
  		status = "blocked"
  	else
  		status = "connection"
  	end

	currentConnection = Connection.new(orccArgs[2], orccArgs[3],orccArgs[4],orccArgs[5],orccArgs[6], fifoContent, status)

	OUT_FILE.write('
				<tr>
                  <td class="left">' + currentConnection.sourceActor + '</td>
                  <td class="right">' + currentConnection.sourcePort + '</td>
                  <td class="' + currentConnection.fifoStatus + '">&mdash;&mdash;&mdash; ' + currentConnection.remainingTokens.to_s + '/' + currentConnection.fifoSize.to_s +  '&mdash;&mdash;&rarr;</td>
                  <td class="left">' + currentConnection.targetPort + '</td>
                  <td class="right">' + currentConnection.targetActor + '</td>
              	</tr>')

end
OUT_FILE.write(
'          
		  </thead>
      </table>

  </body>
</html>')
OUT_FILE.close
