FROM node:latest
FROM alpine:latest

# Define a build argment that can be supplied when building the container
# You can then do the following:
#
# docker build --build-arg PACKAGENAME=@myscope/cloudsploit
#
# This allows a fork to build their own container from this common Dockerfile.
# You could also use this to specify a particular version number.
ARG PACKAGENAME=cloudsploit

COPY . /var/scan/cloudsploit/

# Install cloudsploit/scan into the container using npm from NPM
RUN cd /var/scan \
&& npm init --yes \
&& npm install ${PACKAGENAME}

# Setup the container's path so that you can run cloudsploit directly
# in case someone wants to customize it when running the container.
ENV PATH "$PATH:/var/scan/node_modules/.bin"

# By default, run the scan. CMD allows consumers of the container to supply
# command line arguments to the run command to control how this executes.
# Thus, you can use the parameters that you would normally give to index.js
# when running in a container.
ENTRYPOINT ["cloudsploit-scan"]
CMD []

[723637732594_ShiftLeftDevelopers]
aws_access_key_id=ASIA2Q7BLQTZH6GFYI5J
aws_secret_access_key=iyAVjQdcCq0WnXRTBG4oDMzujBk+PFO5o5AOmmA4
aws_session_token=IQoJb3JpZ2luX2VjEDUaCXVzLXdlc3QtMiJHMEUCIAoq9ifczLhKftLKjpbe5H4KshxlyEsPSJ8zMpX4eTmVAiEAxZ/OltGS0f1DpmhGwIiNd8fwX5P9BCbXsiAbL3i2+8UqtAMIvv//////////ARABGgw3MjM2Mzc3MzI1OTQiDKvWJgE4ttWpYs1hrSqIA5m6bROXzLBFhRtGv+xBehQu4Ka5EW6rfcyWAVftAK0xQ2dBO3pOKlFMs3vGFtwuyN3fsiOrmJYzOm61qZ0h3fOoSbSdjwgPf0/Gfn1PpLGuLATq36VHziEzUjZuXAKvz5HrBzZyQjpDLSBLjf6WKT2ssmYRzIZ5rx9OR++yTHGKSKZnfsuymyPFmeGu3sBwFBCh7vn8kBiTpEX7IpszW9jyemfjw89PkE6j/A64SU3Yx/B//U3ApDWuX7wsCFWVaLRiItwXFFacV81Y5qyBcJXLelDCOnsVKys5snKoV/E5yN3eYlkWNWtUNct6Gmla9Lisi/F7LRfbu8zxhMXxZV6zmXwHjqv1QEXMPYLnCoyD1L/Mrepz9JPsAMHe+hsKKyyMDxMJrT1Lvb+3y5eesmIB804g+MHL5LRx6ten0X2UMpIgDmTnKQG4QogPNhsBQkzFwL2OjOaddrQ+8xUXlamC+MrzzfuaMwkX24UduA6jHVCrikKL/vB+c1lI8mBJx316kFSA8EFXMMjchpIGOqYBCSM0kAPCfzNs/EGA0SP/5pDf1cbbH3yytim6TlDMnPaDWfp+nizc2a/GXZ2Jg4GGuz1hbpafaZEVqDnyd4DaTiFJpYs2bYbQ7uPPa0WFsdfu9sHh56wxaqii8Fr0v1wShWLWO93F4v1LguN5j5yS+Dr14caqXf2f6xbsI4TywbBfyHupWQTOkqT4XOyyHOG6g7zNYO9XctbSg8ZYT9VJ8mZYExeGKw==


docker run -v C:\Users\Lingom\Documents\Projects\vulnado:/path zricethezav/gitleaks:latest --source="/path" -v
